package com.patitosoft.encora.patitosoftapp.controller;

import com.patitosoft.encora.patitosoftapp.resource.DTOAssignSalary;
import com.patitosoft.encora.patitosoftapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/admin/employees/assign-salary")
    private ResponseEntity employeeAssignSalary(@RequestBody DTOAssignSalary dtoAssignSalary) {
        return ResponseEntity.ok(employeeService.assignSalary(dtoAssignSalary));
    }

    @GetMapping("/admin/employee/info-gender")
    private ResponseEntity getInfoGender() {
        return ResponseEntity.ok(employeeService.getInfoGender());
    }

    @GetMapping("/admin/employee/salary-ranges")
    private ResponseEntity getEmployeesBySalaryRanges(@RequestParam(value = "min_salary") Integer minSalary,
                                                      @RequestParam(value = "max_salary") Integer maxSalary) {
        return ResponseEntity.ok(employeeService.getEmployeesBySalaryRanges(minSalary, maxSalary));
    }

    @GetMapping("/employee/{employee_id}")
    private ResponseEntity getEmployee(@PathVariable(value = "employee_id") String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

}
