package com.patitosoft.encora.patitosoftapp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import com.patitosoft.encora.patitosoftapp.domain.Gender;
import com.patitosoft.encora.patitosoftapp.domain.Position;
import com.patitosoft.encora.patitosoftapp.repository.EmployeePositionRepository;
import com.patitosoft.encora.patitosoftapp.repository.EmployeeRepository;
import com.patitosoft.encora.patitosoftapp.repository.PositionRepository;
import com.patitosoft.encora.patitosoftapp.resource.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EmployeePositionRepository employeePositionRepository;

    @Transactional
    public EmployeePositionResource assignSalary(DTOAssignSalary dtoAssignSalary) {
        Employee employee = employeeRepository.findById(dtoAssignSalary.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found ".concat(dtoAssignSalary.getEmployeeId())
                ));
        Position position = positionRepository.findById(dtoAssignSalary.getPositionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Position not found ".concat(dtoAssignSalary.getPositionId())
                ));
        EmployeePosition employeePosition = new EmployeePosition();
        employeePosition.setEmployee(employee);
        employeePosition.setPosition(position);
        employeePosition.setPositionName(position.getName());
        employeePosition.setSalary(dtoAssignSalary.getSalary());
        employeePositionRepository.disableOlderPositionsByEmployee(employee);
        return new EmployeePositionResource.EmployeePositionResourceBuilder
                (employeePositionRepository.save(employeePosition))
                .build();
    }

    public DTOGender getInfoGender() {

        DTOGender dtoGender = new DTOGender();
        List<Employee> employees = employeeRepository.findAll();
        if (CollectionUtils.isEmpty(employees)) {
            dtoGender.setTotalPersons((long) employees.size());
            return dtoGender;
        }
        dtoGender.setTotalPersons((long) employees.size());
        dtoGender.setMale(employeeRepository.countByGender(Gender.MALE));
        dtoGender.setFemale(employeeRepository.countByGender(Gender.FEMALE));
        return dtoGender;
    }

    public DTORangeSalaries getEmployeesBySalaryRanges(Integer minSalary, Integer maxSalary) {

        DTORangeSalaries dtoRangeSalaries = new DTORangeSalaries();
        dtoRangeSalaries.setMaxSalary(employeePositionRepository.maxSalary());
        dtoRangeSalaries.setMinSalary(employeePositionRepository.minSalary());
        dtoRangeSalaries.setCountEmployeesRange(employeePositionRepository.countEmployeesRange(minSalary, maxSalary));

        return dtoRangeSalaries;
    }

    public EmployeeResource getEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found ".concat(employeeId)
                ));
        return new EmployeeResource.EmployeeResourceBuilder(employee).build();
    }

    public List<EmployeeResource> getEmployeeByNamesPosition(String firstName, String lastName, String positionId) {

        List<Employee> employees = employeeRepository.findByFirstNameContainsIgnoreCaseAndIsExEmployee(firstName
                , false);
        employees.addAll(employeeRepository.findByLastNameContainsIgnoreCaseAndIsExEmployee(lastName, false));
        employees.addAll(employeePositionRepository.findAllByPosition_IdAndCurrentPosition(positionId, true)
                .stream().map(EmployeePosition::getEmployee).collect(Collectors.toList()));
        employees.removeAll(Collections.singleton(null));
        employees = employees.stream().filter(employee -> employee.getIsExEmployee().equals(false))
                .collect(Collectors.toList());
        employees = Lists.newArrayList(Sets.newLinkedHashSet(employees));

        return employees.stream()
                .map(employee -> new EmployeeResource.EmployeeResourceBuilder(employee).build())
                .collect(Collectors.toList());
    }

    public List<EmployeeResource> getEmployeeByNamesPosition(String firstName, String lastName, String positionId
            , Boolean exEmployee) {

        List<Employee> employees;
        if (exEmployee) {
            employees = employeeRepository.findByFirstNameStartingWith(firstName);
            employees.addAll(employeeRepository.findByLastNameStartingWith(lastName));
            employees.addAll(employeePositionRepository.findAllByPosition_IdAndCurrentPosition(positionId, true)
                    .stream().map(EmployeePosition::getEmployee).collect(Collectors.toList()));
            employees.removeAll(Collections.singleton(null));
        } else {
            employees = employeeRepository.findByFirstNameContainsIgnoreCaseAndIsExEmployee(firstName, false);
            employees.addAll(employeeRepository.findByLastNameContainsIgnoreCaseAndIsExEmployee(lastName, false));
            employees.addAll(employeePositionRepository.findAllByPosition_IdAndCurrentPosition(positionId, true)
                    .stream().map(EmployeePosition::getEmployee).collect(Collectors.toList()));
            employees.removeAll(Collections.singleton(null));
            employees = employees.stream().filter(employee -> employee.getIsExEmployee().equals(false))
                    .collect(Collectors.toList());
        }
        employees = Lists.newArrayList(Sets.newLinkedHashSet(employees));

        return employees.stream()
                .map(employee -> new EmployeeResource.EmployeeResourceBuilder(employee).build())
                .collect(Collectors.toList());
    }

    public DTOBirthday getEmployeesByBirthday() {

        DTOBirthday dtoBirthday = new DTOBirthday();

        dtoBirthday.setTodayBirthday(employeeRepository.findByBirthday(LocalDate.now()).stream()
                .map(employee -> new EmployeeResource.EmployeeResourceBuilder(employee).build())
                .collect(Collectors.toList()));
        dtoBirthday.setNextWeekBirthday(employeeRepository.findByBirthdayBetween(LocalDate.now()
                        , LocalDate.now().plusWeeks(1)).stream()
                .map(employee -> new EmployeeResource.EmployeeResourceBuilder(employee).build())
                .collect(Collectors.toList()));
        return dtoBirthday;
    }

    @Transactional
    public EmployeeResource updateToExEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found ".concat(employeeId)
                ));
        employee.setIsExEmployee(true);
        return new EmployeeResource.EmployeeResourceBuilder(employeeRepository.save(employee)).build();
    }
}
