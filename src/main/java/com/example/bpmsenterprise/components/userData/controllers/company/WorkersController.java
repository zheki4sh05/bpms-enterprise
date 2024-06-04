package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import com.example.bpmsenterprise.components.userData.interfaces.IUserActivityControl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkersController {

    private final ICompanyControl companyControl;
    private final IUserActivityControl userActivityControl;

    @CrossOrigin
    @GetMapping("/list")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getAllWorkers(@RequestHeader Map<String, String> headers,
                                           @RequestParam(value = "companyName") String companyName) {

        try {
            List<ViewUserAsWorker> workers = companyControl.getWorkers(companyName);

            return new ResponseEntity<>(workers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }

    @CrossOrigin
    @GetMapping("/relevant")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getRelevantWorkers(@RequestHeader Map<String, String> headers,
                                                @RequestParam(value = "projectId") Integer id,
                                                @RequestParam(value = "start") String startDate,
                                                @RequestParam(value = "deadline") String deadline,
                                                @RequestParam(value = "specialization") Integer specialization
    ) {

        try {
            List<ViewUserAsWorker> workers = userActivityControl.getRelevantFor(id, startDate, deadline, specialization);

            return new ResponseEntity<>(workers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }

}
