package io.mdubovac.EmployeeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.mdubovac.EmployeeManagement.model.Employee;
import io.mdubovac.EmployeeManagement.service.DepartmentService;
import io.mdubovac.EmployeeManagement.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/employees")
	public String index(Model model) {
		List<Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employeeList", employeeList);
		return "employees/index";
	}
	
	@GetMapping("/employees/new")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("departmentList", departmentService.getAllDepartments());
		return "employees/create";
	}
	
	@PostMapping("/employees/store")
	public String store(Employee employee) {
		employeeService.save(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		model.addAttribute("departmentList", departmentService.getAllDepartments());
		return "employees/edit";
	}
	
	@PostMapping("/employees/update/{id}")
	public String update(@PathVariable Long id, Model model, Employee employee) {
		Employee currentEmployee = (Employee) employeeService.getEmployeeById(id);
		currentEmployee.setFirstName(employee.getFirstName());
		currentEmployee.setLastName(employee.getLastName());
		currentEmployee.setEmail(employee.getEmail());
		currentEmployee.setDepartment(employee.getDepartment());
		
		employeeService.save(currentEmployee);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/delete/{id}")
	public String delete(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}
}
