package com.example.bpmsenterprise.components.documents.service;

import com.example.bpmsenterprise.components.assignment.repository.AssignmentHasDocRepo;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.DTO.DocumentSourceDTO;
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
    private final AssignmentHasDocRepo assignmentHasDocRepo;
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
        List<String> props = projectId != 0 ? Arrays.asList(String.valueOf(companyId),
                String.valueOf(projectId),
                multipartFile.getOriginalFilename()
        ) :
                Arrays.asList(String.valueOf(companyId),
                        multipartFile.getOriginalFilename()
                );
        String path = FileProcessor.createPath(props, "/");
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
        documentEntities.forEach(item -> {
            documentInfoDTOS.add(createInfo(item, createDocRequest.getType(), createDocRequest.getOriginalType()));
        });
        return documentInfoDTOS;
    }
    private List<DocumentEntity> uploadDocument(Company company, Integer projectId, CreateDocRequest createDocRequest) throws DocumentUploadException {
        Project project = projectId != 0 ? projectRepo.getReferenceById(createDocRequest.getProjectId()) : null;
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

            User user = userRepository.findById(createDocRequest.getUploadedUser().longValue()).orElseThrow(EntityNotFoundException::new);

            DocumentEntity documentEntity = DocumentEntity.builder()
                    .name(FileProcessor.getFileName(file.getOriginalFilename(), "."))
                    .path(path)
                    .size(FileProcessor.calcToMB(file.getSize()))
                    .loadAt(LocalDate.now())
                    .company(company)
                    .type(createDocRequest.getAlignment().equals("document") ? Type.document : Type.report)
                    .extension(FileProcessor.fileExtension(file.getOriginalFilename()))
                    .user(user)
                    .build();
            Integer id = documentsRepo.save(documentEntity).getId();
            documentEntity.setId(id);
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
                    .user(userRepository.findById(user.getId().longValue()).get())
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

    }
//    @Override
//    public List<DocumentInfoDTO> fetch(String companyName, String type) {
//
//        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
//
//        Company company = companyRepo.findBy(companyName).orElseThrow(EntityNotFoundException::new);
//
//        List<AccessByCompany> accessByCompanies = accessPublicRepo.findByCompanyName(companyName, Type.valueOf(type));
//
//        List<ViewProject> projects = projectControl.getAllProjects(companyName);
//
//        List<AccessByProject> accessByProjectList = new ArrayList<>();
//
//        projects.forEach(project -> {
//            List<AccessByProject> accessList = accessProjectRepo.findByProjectIdAndCompanyId(company.getId(), project.getId(), Type.valueOf(type));
//            accessByProjectList.addAll(accessList);
//        });
//        List<AccessByUser> accessByUserLit = accessUserRepo.findByUserId(company.getId(), user.getId(), Type.valueOf(type));
//        List<DocumentInfoDTO> docPublic = doMapping(accessByCompanies, "Общедоступный", "public");
//        List<DocumentInfoDTO> docProject = doMapping(accessByProjectList, "Проект", "project");
//        List<DocumentInfoDTO> docUser = doMapping(accessByUserLit, "Личный", "user");
//        docPublic.addAll(docProject);
//        docPublic.addAll(docUser);
//        return docPublic;
//    }

    @Override
    public List<DocumentInfoDTO> fetch(Company company, List<ViewProject> projects, User user, String type) {

        List<AccessByCompany> accessByCompanies = accessPublicRepo.findByCompanyName(company.getName(), Type.valueOf(type));

        List<AccessByProject> accessByProjectList = new ArrayList<>();

        projects.forEach(project -> {
            List<AccessByProject> accessList = accessProjectRepo.findByProjectIdAndCompanyId(company.getId(), project.getId(), Type.valueOf(type));
            accessByProjectList.addAll(accessList);
        });
        List<AccessByUser> accessByUserLit = accessUserRepo.findByUserId(company.getId(), user.getId(), Type.valueOf(type));
        List<DocumentInfoDTO> docPublic = doMapping(accessByCompanies, "Общедоступный", "public");
        List<DocumentInfoDTO> docProject = doMapping(accessByProjectList, "Проект", "project");
        List<DocumentInfoDTO> docUser = doMapping(accessByUserLit, "Личный", "user");
        docPublic.addAll(docProject);
        docPublic.addAll(docUser);
        return docPublic;
    }

    @Override
    public DocumentSourceDTO getDocInfo(Integer id, String type) {
        DocumentSourceDTO documentSourceDTO = new DocumentSourceDTO();
        switch (type) {
            case "public" -> {

            }
            case "project" -> {
                List<Integer> projects = accessProjectRepo.getProjectsIdByDocId(id);
                documentSourceDTO.setProjects(projects);
            }
            case "user" -> {
                List<Integer> workers = accessUserRepo.getUsersIdByDocId(id);
                documentSourceDTO.setWorkers(workers);
            }
            default -> {

            }
        }
        List<Integer> assignments = assignmentHasDocRepo.getAssignmentsIdByDocId(id);
        documentSourceDTO.setAssignments(assignments);

        return documentSourceDTO;
    }
    @Override
    public List<DocumentInfoDTO> docAssignment(Integer companyId, Integer userId, Integer projectId, String type) {


        List<AccessByProject> accessByProjectList = accessProjectRepo.findByProjectIdAndCompanyId(companyId, projectId, Type.valueOf(type)).stream().filter(item -> item.getDocumentEntity().getType().toString().equals(type)).toList();

//        List<AccessByUser> accessByUsers = documentsRepo.findByAdminAndParticipantId(user.getId(), userId);

        List<DocumentInfoDTO> list = doMapping(accessByProjectList, "Проект", "project");

        return list;
    }
    private <T extends AccessType, R> List<R> doMapping(List<T> entityList, String access, String aPublic) {

        List<R> list = new ArrayList<>();

        entityList.forEach(item -> {

            list.add((R) createInfo(item.getFile(), access, aPublic));
        });

        return list;
    }
    public DocumentInfoDTO createInfo(DocumentEntity item, String access, String aPublic) {
        DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .format(item.getExtension())
                .downloadAt(item.getLoadAt())
                .size(item.getSize())
                .access(access)
                .accessType(aPublic)
                .build();
        return documentInfoDTO;
    }


}
