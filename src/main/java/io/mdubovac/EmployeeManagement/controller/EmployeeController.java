package io.mdubovac.EmployeeManagement.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.mdubovac.EmployeeManagement.FileUploadUtil;
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
	public String store(Employee employee, @RequestParam("file") MultipartFile multipartFile) throws IOException {
	
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		employee.setPhotos(fileName);
	         
		employeeService.save(employee);
		
		String uploadDir = "user-photos/" + employee.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
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
	
	@PostMapping("/employees/change_image/{id}")
	public String changeImage(@PathVariable Long id, Employee employee, @RequestParam("file") MultipartFile multipartFile) throws IOException {
		// Get current employee
		Employee currentEmployee = (Employee) employeeService.getEmployeeById(id);
		
		// Handle Image
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		// Delete current image
		File file = new File("user-photos/" + employee.getId() + "/" + currentEmployee.getPhotos());
		file.delete();
		
		// Set new image
		currentEmployee.setPhotos(fileName);
		
		// Upload image
		String uploadDir = "user-photos/" + employee.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);	
		
		employeeService.save(currentEmployee);
		return "redirect:/employees)";
	}
	
	@GetMapping("/employees/delete/{id}")
	public String delete(@PathVariable Long id) throws IOException {
		// Get current Employee
		Employee currentEmployee = (Employee) employeeService.getEmployeeById(id);
		
		// Delete image from user-photos directory
		File file = new File("user-photos/" + currentEmployee.getId());
		FileUtils.deleteDirectory(new File(file.getPath()));
	
		// Delete employee
		employeeService.deleteEmployee(currentEmployee.getId());	
		
		return "redirect:/employees";
	}
}
