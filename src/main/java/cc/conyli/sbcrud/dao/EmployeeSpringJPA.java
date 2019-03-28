package cc.conyli.sbcrud.dao;

import cc.conyli.sbcrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "members")
public interface EmployeeSpringJPA extends JpaRepository<Employee, Integer> {

}
