package com.example.bpmsenterprise.components.documents.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.entity.Type;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByCompany;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByProject;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByUser;
import com.example.bpmsenterprise.components.documents.exceptions.DocumentUploadException;
import com.example.bpmsenterprise.components.documents.interfaces.AccessType;
import com.example.bpmsenterprise.components.documents.interfaces.IDocumentsControl;
import com.example.bpmsenterprise.components.documents.props.CreateDocRequest;
import com.example.bpmsenterprise.components.documents.props.MinioProperties;
import com.example.bpmsenterprise.components.documents.repos.AccessProjectRepo;
import com.example.bpmsenterprise.components.documents.repos.AccessPublicRepo;
import com.example.bpmsenterprise.components.documents.repos.AccessUserRepo;
import com.example.bpmsenterprise.components.documents.repos.DocumentsRepo;
import com.example.bpmsenterprise.components.documents.util.FileProcessor;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentsService implements IDocumentsControl {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final DocumentsRepo documentsRepo;
    private final CompanyRepo companyRepo;
    private final ProjectRepo projectRepo;
    private final AccessPublicRepo accessPublicRepo;
    private final AccessProjectRepo accessProjectRepo;
    private final AccessUserRepo accessUserRepo;
    private final UserRepository userRepository;
    private final IProjectControl projectControl;
    private final UserData userData;

    private String generateFileName(
            final MultipartFile file
    ) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(
            final MultipartFile file
    ) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }


    private String saveDocument(
            final InputStream inputStream,
            final Integer companyId,
            final Integer projectId,
            final MultipartFile multipartFile

    ) throws DocumentUploadException {

        String path = FileProcessor.createPath(Arrays.asList(String.valueOf(companyId),
                String.valueOf(projectId),
                multipartFile.getOriginalFilename()
        ), "/");
        System.out.println("bucket " + minioProperties.getDocumentsBucket());
        System.out.println("path " + path);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getDocumentsBucket())
                    .object(path)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 IOException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new DocumentUploadException(e.getMessage());
        }
        return path;

    }

    @Override
    public List<DocumentInfoDTO> upload(CreateDocRequest createDocRequest) throws DocumentUploadException {

        Company company = companyRepo.findBy(createDocRequest.getCompanyName()).orElseThrow(EntityExistsException::new);

        List<DocumentEntity> documentEntities;
        List<DocumentInfoDTO> documentInfoDTOS = new ArrayList<>();

        documentEntities = uploadDocument(company, createDocRequest.getProjectId(), createDocRequest);

//        switch(createDocRequest.getAlignment()){
//            case "report":{
//
//                documentEntities  = uploadReport(company,createDocRequest.getProjectId(), createDocRequest);
//
//                break;
//            }
//            default:{
//
//                break;
//            }
//        }


        documentEntities.forEach(item -> {

//            accessDispatcher(createDocRequest.getType(), company, projectRepo.getReferenceById(createDocRequest.getProjectId()), item, createDocRequest.getWorkers(), createDocRequest.getByRequest());


            documentInfoDTOS.add(createInfo(item, createDocRequest.getType()));
        });


        return documentInfoDTOS;
    }


//    private List<DocumentEntity> uploadReport(Company company, Integer projectId, CreateDocRequest createDocRequest) {
//
//        List<DocumentEntity> list = new ArrayList<>();
//
//    }

    private List<DocumentEntity> uploadDocument(Company company, Integer projectId, CreateDocRequest createDocRequest) throws DocumentUploadException {

        Project project = projectRepo.getReferenceById(createDocRequest.getProjectId());

        List<DocumentEntity> list = new ArrayList<>();


        for (MultipartFile file : createDocRequest.getFile()) {

            if (file.isEmpty() || file.getOriginalFilename() == null) {
                throw new DocumentUploadException("Document must have name.");
            }
            InputStream inputStream;
            try {
                inputStream = file.getInputStream();
            } catch (Exception e) {
                throw new DocumentUploadException("Document upload failed: "
                        + e.getMessage());
            }

            String path = saveDocument(inputStream, company.getId(), projectId, file);

            DocumentEntity documentEntity = DocumentEntity.builder()
                    .name(FileProcessor.getFileName(file.getOriginalFilename(), "."))
                    .path(path)
                    .size(FileProcessor.calcToMB(file.getSize()))
                    .loadAt(LocalDate.now())
                    .company(company)
                    .type(createDocRequest.getAlignment().equals("document") ? Type.document : Type.report)
                    .extension(FileProcessor.fileExtension(file.getOriginalFilename()))
                    .build();


            Integer id = documentsRepo.save(documentEntity).getId();
            documentEntity.setId(id);
            if (createDocRequest.getAlignment().equals("document"))
                accessDispatcher(createDocRequest.getOriginalType(), company, project, documentEntity, createDocRequest.getWorkers(), createDocRequest.getByRequest());

            list.add(documentEntity);

        }
        return list;
    }

    private void accessDispatcher(String type, Company company, Project project, DocumentEntity documentEntity, List<ViewUserAsWorker> workers, Boolean byRequest) {
        switch (type) {
            case "project": {

                createAccessByProject(company, project, documentEntity, byRequest);

                break;
            }
            case "user": {

                createAccessByUser(company, documentEntity, workers, byRequest);

                break;
            }
            default: {

                createAccessByPublic(company, documentEntity);

                break;
            }
        }

    }

    private void createAccessByUser(Company company, DocumentEntity documentEntity, List<ViewUserAsWorker> workers, Boolean byRequest) {

        workers.forEach(user -> {
            AccessByUser access = AccessByUser.builder()
                    .user(userRepository.findByEmail(user.getEmail()).get())
                    .byRequest(byRequest)
                    .documentEntity(documentEntity)
                    .company(company)
                    .build();

            accessUserRepo.save(access);
        });


    }

    private void createAccessByProject(Company company, Project project, DocumentEntity documentEntity, Boolean byRequest) {

        AccessByProject access = AccessByProject.builder()
                .project(project)
                .byRequest(byRequest)
                .company(company)
                .documentEntity(documentEntity)
                .build();

        accessProjectRepo.save(access);

    }

    private void createAccessByPublic(Company company, DocumentEntity documentEntity) {


        AccessByCompany access = AccessByCompany.builder()
                .company(company)
                .documentEntity(documentEntity)
                .build();
        AccessByCompany company1 = accessPublicRepo.save(access);
        System.out.println(company1.getCompany().getName());


    }

    @Override
    public List<DocumentInfoDTO> fetch(String alignment, String companyName) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        Company company = companyRepo.findBy(companyName).orElseThrow(EntityNotFoundException::new);

        List<AccessByCompany> accessByCompanies = accessPublicRepo.findByCompanyName(companyName);

        List<ViewProject> projects = projectControl.getAllProjects(companyName);
        List<AccessByProject> accessByProjectList = new ArrayList<>();
        projects.forEach(project -> {
            List<AccessByProject> accessList = accessProjectRepo.findByProjectIdAndCompanyId(company.getId(), project.getId());
            accessByProjectList.addAll(accessList);
        });

        List<AccessByUser> accessByUserLit = accessUserRepo.findByUserId(company.getId(), user.getId());


        List<DocumentInfoDTO> docPublic = doMapping(accessByCompanies, "Общедоступный"); //дополнить интерфейс AccessType методами и сделать doMapping дженериком

        List<DocumentInfoDTO> docProject = doMapping(accessByProjectList, "Проект");

        List<DocumentInfoDTO> docUser = doMapping(accessByUserLit, "Личный");

        docPublic.addAll(docProject);
        docPublic.addAll(docUser);

        return docPublic;
    }

    private <T extends AccessType, R> List<R> doMapping(List<T> entityList, String access) {

        List<R> list = new ArrayList<>();

        entityList.forEach(item -> {

            list.add((R) createInfo(item.getFile(), access));
        });

        return list;
    }

    private DocumentInfoDTO createInfo(DocumentEntity item, String access) {
        DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .format(item.getExtension())
                .downloadAt(item.getLoadAt())
                .size(item.getSize())
                .access(access)
                .build();
        return documentInfoDTO;
    }


}
