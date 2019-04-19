package cc.conyli.sbcrud.rest;

import cc.conyli.sbcrud.entity.Employee;
import cc.conyli.sbcrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRESTController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRESTController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() throws InterruptedException {
        Thread.sleep(4000);
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id {{" + employeeId + "}} NOT FOUND");
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        employeeService.save(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        //无需先判断是否存在，否则会造成重复引用同一个数据，会报错。
        employeeService.save(employee);
        return employee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public Employee deleteEmployee(@PathVariable int employeeId) {
        Employee targetEmployee = employeeService.findById(employeeId);
        if (targetEmployee == null) {
            throw new RuntimeException("Employee id {{" + employeeId + "}} NOT FOUND");
        }
        employeeService.deleteById(employeeId);
        return targetEmployee;
    }


}
