package io.mdubovac.EmployeeManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mdubovac.EmployeeManagement.model.Department;
import io.mdubovac.EmployeeManagement.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	// List of departments
	public List<Department> getAllDepartments() {
		List<Department> departmentList = departmentRepository.findAll();
		return departmentList;
	}

	// Saves a department
	public void save(Department department) {
		departmentRepository.save(department);
	}

	// Returns single department by id
	public Department getDepartmentById(Long id) {
		Department department = departmentRepository.findById(id).get();
		return department;
	}

	// Deletes a department by id
	public void deleteById(Long id) {
		departmentRepository.deleteById(id);
	}
	
}
