package com.patitosoft.encora.patitosoftapp.controller;

import com.patitosoft.encora.patitosoftapp.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/admin/position/each-position")
    private ResponseEntity getEmployeesEachPosition() {
        return ResponseEntity.ok(positionService.getEmployeesEachPosition());
    }
}
