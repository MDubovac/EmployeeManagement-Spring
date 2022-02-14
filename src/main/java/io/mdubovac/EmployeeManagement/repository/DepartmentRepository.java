package io.mdubovac.EmployeeManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mdubovac.EmployeeManagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
