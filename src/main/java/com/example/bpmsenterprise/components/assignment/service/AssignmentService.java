package com.example.bpmsenterprise.components.assignment.service;

import com.example.bpmsenterprise.components.assignment.DTO.AssignmentDTO;
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
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.repos.DocumentsRepo;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Integer createNewAssignment(CreateAssignmentRequest assignmentRequest) {

        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        //------Сохраняем сущность Поручение-----------
        Assignment assignment = Assignment.builder()
                .name(assignmentRequest.getName())
                .description(assignmentRequest.getDesc())
                .startAt(AssignmentProcessor.parseDate(assignmentRequest.getStartDate()))
                .deadline(AssignmentProcessor.parseDate(assignmentRequest.getFinishDate()))
                .status(Status.created)
                .build();

        Assignment saved = assignmentRepo.save(assignment);
        //-----------------------------

        //---Получаем и сохраняем документы и отчеты
        List<DocumentEntity> docs = documentsRepo.findAllById(assignmentRequest.getDocuments().stream().map(Long::valueOf).toList());
        List<DocumentEntity> reports = documentsRepo.findAllById(assignmentRequest.getReports().stream().map(Long::valueOf).toList());
        List<AssignmentDocument> assignmentDocumentList = getDocEntities(docs, saved);
        List<AssignmentDocument> assignmentReportsList = getDocEntities(reports, saved);

        assignmentHasDocRepo.saveAll(assignmentDocumentList);
        assignmentHasDocRepo.saveAll(assignmentReportsList);

        //-----------------------------

        //---Создаем ассоциацию Инициатор-Исполнитель-Проект ------------
        User worker = userRepository.findById(assignmentRequest.getId().longValue()).orElseThrow(EntityNotFoundException::new);
        Project project = projectRepo.findById(assignmentRequest.getProjectId()).orElseThrow(EntityNotFoundException::new);
        Association association = Association.builder()
                .user(user)
                .worker(worker)
                .project(project)
                .assignment(saved)
                .build();

        associationRepo.save(association);
        //-----------------------------

        //---Сохраняем Туду лист-----

        List<TodoEntity> todoEntities = new ArrayList<>();

        assignmentRequest.getTasks().forEach(item -> {

            TodoEntity todoEntity = TodoEntity.builder()
                    .name(item.getVal())
                    .isDone(item.getIsDone())
                    .assignment(saved)
                    .build();

            todoEntities.add(todoEntity);

        });

        toDoRepo.saveAll(todoEntities);

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

    private List<AssignmentDTO> doMapping(List<Association> associationList) {

        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();

        associationList.forEach(item -> {
            AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                    .id(item.getId())
                    .createdAt(item.getAssignment().getCreatedAt())
                    .deadline(item.getAssignment().getDeadline())
                    .status(item.getAssignment().getStatus())
                    .statusName(getStatusName(item.getAssignment().getStatus()))
                    .stageId(1)
                    .name(item.getAssignment().getName())
                    .description(item.getAssignment().getDescription())
                    .user(item.getUser().getId())
                    .worker(item.getWorker().getId())
                    .projectId(item.getProject().getId())
                    .startAt(item.getAssignment().getStartAt())
                    .build();

            assignmentDTOS.add(assignmentDTO);
        });

        return assignmentDTOS;
    }

    private String getStatusName(Status status) {
        switch (status.name()) {
            case "created" -> {
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
            default -> {
                return "Приостановлено";
            }


        }

    }
}
