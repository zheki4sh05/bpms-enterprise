package com.example.bpmsenterprise.components.assignment.service;

import com.example.bpmsenterprise.components.assignment.DTO.*;
import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;
import com.example.bpmsenterprise.components.assignment.entity.*;
import com.example.bpmsenterprise.components.assignment.interfaces.IAssigmentControl;
import com.example.bpmsenterprise.components.assignment.repository.AssignmentHasDocRepo;
import com.example.bpmsenterprise.components.assignment.repository.AssignmentRepo;
import com.example.bpmsenterprise.components.assignment.repository.AssociationRepo;
import com.example.bpmsenterprise.components.assignment.repository.ToDoRepo;
import com.example.bpmsenterprise.components.assignment.util.AssignmentProcessor;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.DTO.ToDoDTO;
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.props.CreateDocRequest;
import com.example.bpmsenterprise.components.documents.repos.DocumentsRepo;
import com.example.bpmsenterprise.components.documents.service.DocumentsService;
import com.example.bpmsenterprise.components.documents.util.FileProcessor;
import com.example.bpmsenterprise.components.userData.UserUtil;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.Stage;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import com.example.bpmsenterprise.components.userData.repository.StagesRepo;
import com.example.bpmsenterprise.components.userData.repository.UserSpecInCompanyRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssigmentControl {

    private final UserData userData;
    private final UserRepository userRepository;
    private final AssignmentRepo assignmentRepo;
    private final ProjectRepo projectRepo;
    private final AssociationRepo associationRepo;
    private final DocumentsRepo documentsRepo;
    private final AssignmentHasDocRepo assignmentHasDocRepo;
    private final ToDoRepo toDoRepo;
    private final StagesRepo stagesRepo;
    private final UserSpecInCompanyRepo userSpecInCompanyRepo;

    @Override
    public Integer createNewAssignment(CreateAssignmentRequest assignmentRequest) {

        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        Project project = projectRepo.findById(assignmentRequest.getProjectId()).orElseThrow(EntityNotFoundException::new);

        List<Stage> list = stagesRepo.findByProjectId(project.getId());
        Stage stage = null;
        if (list != null) {
            stage = list.get(0);
        }

        //------Сохраняем сущность Поручение-----------
        Assignment assignment = Assignment.builder()
                .name(assignmentRequest.getName())
                .description(assignmentRequest.getDesc())
                .createdAt(LocalDate.now())
                .startAt(AssignmentProcessor.parseDate(assignmentRequest.getStartDate()))
                .deadline(assignmentRequest.getFinishDate())
                .status(Status.create)
                .stage(stage)
                .build();

        Assignment saved = assignmentRepo.save(assignment);
        //-----------------------------

        //---Получаем и сохраняем документы и отчеты
//        List<DocumentEntity> docs = documentsRepo.findAllById(assignmentRequest.getDocuments().stream().map(Long::valueOf).toList());
//        List<DocumentEntity> reports = documentsRepo.findAllById(assignmentRequest.getReports().stream().map(Long::valueOf).toList());
//        List<AssignmentDocument> assignmentDocumentList = getDocEntities(docs, saved);
//        List<AssignmentDocument> assignmentReportsList = getDocEntities(reports, saved);
//
//        assignmentHasDocRepo.saveAll(assignmentDocumentList);
//        assignmentHasDocRepo.saveAll(assignmentReportsList);

        saveAssignmentsDocs(assignmentRequest.getDocuments().stream().map(Long::valueOf).toList(),
                assignmentRequest.getReports().stream().map(Long::valueOf).toList(),
                saved);

        //-----------------------------

        //---Создаем ассоциацию Инициатор-Исполнитель-Проект ------------
        User worker = userRepository.findById(assignmentRequest.getId().longValue()).orElseThrow(EntityNotFoundException::new);

        Association association = Association.builder()
                .user(user)
                .worker(worker)
                .project(project)
                .assignment(saved)
                .build();

        associationRepo.save(association);
        //-----------------------------

        //---Сохраняем Туду лист-----

        assignmentRequest.getTasks().forEach(item -> {

            TodoEntity todoEntity = TodoEntity.builder()
                    .name(item.getVal())
                    .isDone(item.getIsDone())
                    .assignment(saved)
                    .build();

            toDoRepo.save(todoEntity);

        });


        //-------------------------


        return saved.getId();
    }


    private List<AssignmentDocument> getDocEntities(List<DocumentEntity> documents, Assignment saved) {

        List<AssignmentDocument> list = new ArrayList<>();

        documents.forEach(item -> {
            AssignmentDocument assignmentDocument = AssignmentDocument.builder()
                    .assignment_id(saved)
                    .document_id(item)
                    .build();

            list.add(assignmentDocument);

        });

        return list;

    }

    @Override
    public List<AssignmentDTO> fetch(String userEmail, String role, String size) {

        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        List<Association> associationListUsers = associationRepo.findAllByUserId(user.getId());

        List<Association> associationListWorkers = associationRepo.findAllByWorkerId(user.getId());

        List<AssignmentDTO> assignmentUserDTOS = doMapping(associationListUsers);
        List<AssignmentDTO> assignmentWorkerDTOS = doMapping(associationListWorkers);
        assignmentUserDTOS.addAll(assignmentWorkerDTOS);

        return assignmentUserDTOS;
    }

    @Override
    public AssignmentDTOStatuses assignmentStatuses(Integer id) {

        AssignmentDTOStatuses assignmentDTOStatuses = new AssignmentDTOStatuses();

        Association association = associationRepo.findByAssignmentId(id);

        User workerDAO = association.getWorker();
        Assignment assignment = association.getAssignment();
        ViewUserAsWorker worker = UserUtil.doMapFrom(workerDAO);
        Integer spec = userSpecInCompanyRepo.findBySpecUserId(workerDAO.getId()).getSpecialiazation().getId();
        worker.setSpec(spec);

        assignmentDTOStatuses.setViewUserAsWorker(worker);

        List<ToDoDTO> toDoDTOList = new ArrayList<>();
        toDoRepo.findAllByAssignmentId(assignment.getId()).forEach(
                item -> {

                    ToDoDTO toDoDTO = ToDoDTO.builder()
                            .val(item.getName())
                            .isDone(item.getIsDone())
                            .assignmentId(assignment.getId())
                            .id(item.getId().longValue())
                            .build();

                    toDoDTOList.add(toDoDTO);

                });

        assignmentDTOStatuses.setToDoDTOList(toDoDTOList);

        List<DocumentInfoDTO> documents = new ArrayList<>();
        List<DocumentInfoDTO> reports = new ArrayList<>();
        List<DocumentEntity> documentEntities = assignmentHasDocRepo.getDocsByAssignmentId(assignment.getId());
        List<DocumentEntity> documentsDAO = documentEntities.stream().filter(item -> item.getType().toString().equals("document")).toList();
        List<DocumentEntity> reportsDAO = documentEntities.stream().filter(item -> item.getType().toString().equals("report")).toList();

        documentsDAO.forEach(item -> {
            DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .format(item.getExtension())
                    .downloadAt(item.getLoadAt())
                    .size(item.getSize())
                    .access("")
                    .accessType("")
                    .build();
            documents.add(documentInfoDTO);

        });

        reportsDAO.forEach(item -> {
            DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .format(item.getExtension())
                    .downloadAt(item.getLoadAt())
                    .size(item.getSize())
                    .access("")
                    .accessType("")
                    .build();
            reports.add(documentInfoDTO);

        });


        assignmentDTOStatuses.setDocuments(documents);
        assignmentDTOStatuses.setReports(reports);


        assignmentDTOStatuses.setStatusNames(

                Stream.of(Status.values()).map(Status::name).collect(Collectors.toList())

        );


        List<DocumentEntity> documentEntityList = assignmentHasDocRepo.findAddedDocs(workerDAO.getId());

        List<DocumentInfoDTO> documentInfoDTOS = new ArrayList<>();

        documentEntityList.forEach(item -> {
            DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .format(item.getExtension())
                    .downloadAt(item.getLoadAt())
                    .size(item.getSize())
                    .access("user")
                    .accessType(FileProcessor.accessName("user"))
                    .build();
            documentInfoDTOS.add(documentInfoDTO);

        });

        assignmentDTOStatuses.setAddedDocuments(documentInfoDTOS);

        return assignmentDTOStatuses;
    }


    @Override
    public AssignmentDTO update(UpdateAssignmentDTO updateAssignment) {

        Assignment assignment = assignmentRepo.findById(updateAssignment.getId()).orElseThrow(EntityNotFoundException::new);

        Stage newStage = stagesRepo.findById(updateAssignment.getNewStage()).orElseThrow(EntityNotFoundException::new);

        assignment.setName(updateAssignment.getName());
        assignment.setDescription(updateAssignment.getDesc());
        assignment.setDeadline(updateAssignment.getDeadline());
        assignment.setStatus(Status.valueOf(updateAssignment.getStatus()));
        assignment.setStage(newStage);
        assignment.setStartAt(updateAssignment.getStartAt());

        assignmentRepo.save(assignment);

        Association item = associationRepo.findByAssignmentId(assignment.getId());

        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(item.getAssignment().getId())
                .createdAt(item.getAssignment().getCreatedAt())
                .deadline(item.getAssignment().getDeadline())
                .status(item.getAssignment().getStatus())
                .statusName(getStatusName(item.getAssignment().getStatus()))
                .stageId(item.getAssignment().getStage() != null ? item.getAssignment().getStage().getId() : 0)
                .stageName(item.getAssignment().getStage() != null ? item.getAssignment().getStage().getName() : "Не назначено")
                .name(item.getAssignment().getName())
                .description(item.getAssignment().getDescription())
                .user(item.getUser().getId())
                .worker(item.getWorker().getId())
                .projectId(item.getProject().getId())
                .startAt(item.getAssignment().getStartAt())
                .build();

        return assignmentDTO;
    }

    @Override
    public Integer docDel(Integer docId) {

        AssignmentDocument assignmentDocument = assignmentHasDocRepo.findByDocId(docId);

        assignmentHasDocRepo.delete(assignmentDocument);

        return assignmentDocument.getDocument_id().getId();
    }

    @Override
    public List<ToDoDTO> updateTodos(UpdateTodosRequest updateTodosRequest) {


        Assignment assignment = assignmentRepo.findById(updateTodosRequest.getAssignmentId()).orElseThrow(EntityNotFoundException::new);

        List<TodoEntity> deleteTodoEntityList = toDoRepo.findAllByAssignmentId(assignment.getId());

        deleteTodoEntityList.forEach(item -> {
            toDoRepo.delete(item);
        });


        List<TodoEntity> todoEntityList = new ArrayList<>();

        updateTodosRequest.getTodos().forEach(item -> {

            TodoEntity todoEntity = new TodoEntity();

            todoEntity.setAssignment(assignment);
            todoEntity.setName(item.getVal());
            todoEntity.setIsDone(item.getIsDone());
            todoEntityList.add(todoEntity);
        });

        List<TodoEntity> savedList = toDoRepo.saveAll(todoEntityList);

        List<ToDoDTO> toDoDTOList = new ArrayList<>();

        savedList.forEach(item -> {
            ToDoDTO toDoDTO = ToDoDTO.builder()
                    .id(item.getId().longValue())
                    .assignmentId(item.getAssignment().getId())
                    .isDone(item.getIsDone())
                    .val(item.getName())
                    .build();
            toDoDTOList.add(toDoDTO);

        });

        return toDoDTOList;
    }

    @Override
    public ViewUserAsWorker updateAssignmentWorker(UpdateAssignmentWorkerDTO updateTodosRequest) {


        Association association = associationRepo.findByAssignmentId(updateTodosRequest.getAssignmentId());

        User user = userRepository.findById(updateTodosRequest.getViewUserAsWorker().getId().longValue()).orElseThrow(EntityNotFoundException::new);

        association.setWorker(user);

        ViewUserAsWorker viewUserAsWorker = ViewUserAsWorker.builder()
                .spec(updateTodosRequest.getSpec())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .id(user.getId())
                .role(updateTodosRequest.getRole())
                .build();

        return viewUserAsWorker;
    }

    @Override
    public AssignmentDTO changeStatus(ChangeAssignmentStatusDTO changeAssignmentStatusDTO) {

        Assignment assignment = assignmentRepo.findById(changeAssignmentStatusDTO.getAssignmentId()).orElseThrow(EntityNotFoundException::new);

        assignment.setStatus(Status.valueOf(changeAssignmentStatusDTO.getNewStatus()));

        assignmentRepo.save(assignment);

        Association association = associationRepo.findByAssignmentId(assignment.getId());

        AssignmentDTO updated = doMapping(List.of(association)).get(0);

        return updated;
    }

    private List<AssignmentDTO> doMapping(List<Association> associationList) {

        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();

        associationList.forEach(item -> {
            AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                    .id(item.getAssignment().getId())
                    .createdAt(item.getAssignment().getCreatedAt())
                    .deadline(item.getAssignment().getDeadline())
                    .status(item.getAssignment().getStatus())
                    .statusName(getStatusName(item.getAssignment().getStatus()))
                    .stageId(item.getAssignment().getStage() != null ? item.getAssignment().getStage().getId() : 0)
                    .stageName(item.getAssignment().getStage() != null ? item.getAssignment().getStage().getName() : "Не назначено")
                    .name(item.getAssignment().getName())
                    .description(item.getAssignment().getDescription())
                    .user(item.getUser().getId())
                    .userEmail(item.getUser().getEmail())
                    .worker(item.getWorker().getId())
                    .projectId(item.getProject().getId())
                    .projectName(item.getProject().getName())
                    .startAt(item.getAssignment().getStartAt())
                    .build();

            assignmentDTOS.add(assignmentDTO);
        });

        return assignmentDTOS;
    }

    private String getStatusName(Status status) {
        switch (status.name()) {
            case "create" -> {
                return "Назначено";
            }
            case "stop" -> {
                return "Приостановлено";
            }
            case "reject" -> {
                return "Отклонено";
            }
            case "done" -> {
                return "Выполнено";
            }
            case "accepted" -> {
                return "Принято";
            }
            default -> {
                return "Приостановлено";
            }
        }
    }

    private List<AssignmentDocument> saveAssignmentsDocs(List<Long> idsDocs, List<Long> idsReports, Assignment assignment) {
        List<DocumentEntity> docs = documentsRepo.findAllById(idsDocs);
        List<DocumentEntity> reports = documentsRepo.findAllById(idsReports);
        List<AssignmentDocument> assignmentDocumentList = getDocEntities(docs, assignment);
        List<AssignmentDocument> assignmentReportsList = getDocEntities(reports, assignment);

        assignmentHasDocRepo.saveAll(assignmentDocumentList);
        assignmentHasDocRepo.saveAll(assignmentReportsList);

        assignmentDocumentList.addAll(assignmentReportsList);
        return assignmentDocumentList;
    }

    @Override
    public List<AssignmentDocument> addDocsAssignment(List<Long> list, CreateDocRequest createDocRequest) {

        Assignment assignment = assignmentRepo.findById(createDocRequest.getAssignmentId()).orElseThrow(EntityNotFoundException::new);

        saveAssignmentsDocs(list, new ArrayList<>(), assignment);

        return saveAssignmentsDocs(list, new ArrayList<>(), assignment);
    }
}
