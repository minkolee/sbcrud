package cc.conyli.sbcrud.service;

import cc.conyli.sbcrud.dao.EmployeeSpringJPA;
import cc.conyli.sbcrud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    //Hibernate和JPA使用的构造器注入
//    private EmployeeDAO employeeDAO;

//    @Autowired
//    public EmployeeServiceImpl(EmployeeDAOJPAImpl employeeDAO) {
//        this.employeeDAO = employeeDAO;
//    }

    private EmployeeSpringJPA employeeSpringJPA;

    @Autowired
    public EmployeeServiceImpl(EmployeeSpringJPA employeeSpringJPA) {
        this.employeeSpringJPA = employeeSpringJPA;
    }

    @Override
    public List<Employee> findAll() {
        return employeeSpringJPA.findAll();
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeSpringJPA.findById(id);
        Employee employee;
        if (result.isPresent()) {
            employee = result.get();
        } else {
            throw new RuntimeException("NOT FOUND {{" + id + "}} NOT FOUND");
        }
        return employee;
    }

    @Override
    public void save(Employee employee) {
        employeeSpringJPA.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeSpringJPA.deleteById(id);
    }
}
