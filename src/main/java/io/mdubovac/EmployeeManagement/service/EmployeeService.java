package io.mdubovac.EmployeeManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mdubovac.EmployeeManagement.model.Employee;
import io.mdubovac.EmployeeManagement.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	// Return a list of employees
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	// Save an employee
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	// Return single employee by id
	public Object getEmployeeById(Long id) {
		Employee employee = employeeRepository.findById(id).get();
		return employee;
	}

	// Delete an employee
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
	
}
