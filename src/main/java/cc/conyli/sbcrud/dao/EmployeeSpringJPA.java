package cc.conyli.sbcrud.dao;

import cc.conyli.sbcrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSpringJPA extends JpaRepository<Employee, Integer> {

}
