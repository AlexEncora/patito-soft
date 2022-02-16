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

    @GetMapping("/employee/search-by-names-position")
    private ResponseEntity getEmployeeByNamesPosition(@RequestParam(value = "firstname") String firstName,
                                                      @RequestParam(value = "lastname") String lastName,
                                                      @RequestParam(value = "position_id") String positionId) {
        return ResponseEntity.ok(employeeService.getEmployeeByNamesPosition(firstName, lastName, positionId));
    }

    @GetMapping("admin/employee/search-by-names-position")
    private ResponseEntity getEmployeeByNamesPosition(@RequestParam(value = "firstname") String firstName,
                                                      @RequestParam(value = "lastname") String lastName,
                                                      @RequestParam(value = "position_id") String positionId,
                                                      @RequestParam(value = "ex_employee") Boolean exEmployee) {
        return ResponseEntity.ok(employeeService.getEmployeeByNamesPosition(firstName, lastName, positionId, exEmployee));
    }

    @GetMapping("/employee/birthday")
    private ResponseEntity getEmployeesByBirthday() {
        return ResponseEntity.ok(employeeService.getEmployeesByBirthday());
    }

    @PutMapping("/employee/{employee_id}")
    private ResponseEntity updateToExEmployee(@PathVariable(value = "employee_id") String employeeId) {
        return ResponseEntity.ok(employeeService.updateToExEmployee(employeeId));
    }

}
