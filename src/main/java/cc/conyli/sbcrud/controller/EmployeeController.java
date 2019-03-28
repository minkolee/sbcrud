package cc.conyli.sbcrud.controller;

import cc.conyli.sbcrud.entity.Employee;
import cc.conyli.sbcrud.service.EmployeeService;
import cc.conyli.sbcrud.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String showEmployeeList(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "list-employees";
    }

    @GetMapping("/form")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "updateform";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id, Model model) {
        employeeService.deleteById(id);
        return "redirect:/list";
    }

}
