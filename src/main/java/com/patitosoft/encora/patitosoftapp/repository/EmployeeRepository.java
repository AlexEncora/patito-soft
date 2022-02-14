package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource(path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Long countByGender(Gender gender);
}
