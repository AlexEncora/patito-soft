package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
