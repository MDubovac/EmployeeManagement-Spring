package io.mdubovac.EmployeeManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mdubovac.EmployeeManagement.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
