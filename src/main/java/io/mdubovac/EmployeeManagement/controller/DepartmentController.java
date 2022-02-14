package io.mdubovac.EmployeeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.mdubovac.EmployeeManagement.model.Department;
import io.mdubovac.EmployeeManagement.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/departments")
	public String index(Model model) {
		List<Department> departmentList = departmentService.getAllDepartments();
		model.addAttribute("departmentList", departmentList);
		return "departments/index";
	}
	
	@GetMapping("/departments/new")
	public String create(Model model) {
		model.addAttribute("department", new Department());
		return "departments/create";
	}
	
	@PostMapping("/departments/store")
	public String store(Department department) {
		departmentService.save(department);
		return "redirect:/departments";
	}
	
	@GetMapping("/departments/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Department department = departmentService.getDepartmentById(id);
		model.addAttribute("department", department);
		return "departments/edit";
	}
	
	@PostMapping("/departments/update/{id}")
	public String update(@PathVariable Long id, Model model, Department department) {
		Department currentDepartment = departmentService.getDepartmentById(id);
		currentDepartment.setName(department.getName());
		
		departmentService.save(currentDepartment);		
		return "redirect:/departments";
	}
	
	@GetMapping("/departments/delete/{id}")
	public String delete(@PathVariable Long id) {
		departmentService.deleteById(id);
		return "redirect:/departments";
	}
	
}
