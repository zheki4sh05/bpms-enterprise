package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.DTO.ChangeSpecDTO;
import com.example.bpmsenterprise.components.userData.DTO.SpecDTO;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.interfaces.ISpecControl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/company/specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final ISpecControl iSpecControl;

    @CrossOrigin
    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> createSpec(@RequestHeader Map<String, String> headers,
                                        @RequestBody SpecDTO data) {
        try {
            SpecDTO specDTO = iSpecControl.create(data.getId(), data.getName());
            return ResponseEntity.ok(specDTO);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @PostMapping("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> updateSpec(@RequestHeader Map<String, String> headers,
                                        @RequestBody SpecDTO data) {
        try {
            SpecDTO specDTO = iSpecControl.update(data.getId(), data.getName(), data.getCount());
            return ResponseEntity.ok(specDTO);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @DeleteMapping("/delete")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteSpec(@RequestHeader Map<String, String> headers,
                                        @RequestParam("id") Integer id) {
        try {
            Integer deleteId = iSpecControl.delete(id);
            return ResponseEntity.ok(deleteId);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @GetMapping("/fetch")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getSpecList(@RequestHeader Map<String, String> headers,
                                         @RequestParam("companyName") String name) {
        try {
            List<SpecDTO> map = iSpecControl.fetch(name);
            return ResponseEntity.ok(map);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @PostMapping("/change")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> chnageUserSpec(@RequestHeader Map<String, String> headers,
                                            @RequestBody ChangeSpecDTO changeSpecDTO) {
        try {
            List<SpecDTO> map = iSpecControl.change(changeSpecDTO);
            return ResponseEntity.ok(map);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }


}
