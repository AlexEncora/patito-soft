package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
@RepositoryRestResource(path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Long countByGender(Gender gender);

    List<Employee> findByFirstNameContainsIgnoreCaseAndIsExEmployee(String prefix, Boolean isEx);

    List<Employee> findByLastNameContainsIgnoreCaseAndIsExEmployee(String prefix, Boolean isEx);

    List<Employee> findByFirstNameStartingWith(String prefix);

    List<Employee> findByLastNameStartingWith(String prefix);

    List<Employee> findByBirthday(LocalDate localDate);

    List<Employee> findByBirthdayBetween(LocalDate start, LocalDate end);
}
