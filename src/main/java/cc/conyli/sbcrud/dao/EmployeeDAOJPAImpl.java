package cc.conyli.sbcrud.dao;

import cc.conyli.sbcrud.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOJPAImpl implements EmployeeDAO {

    private EntityManager entityManager;

    //构造器注入
    public EmployeeDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        System.out.println("JPA API working...");
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        System.out.println("JPA API working...");

        return entityManager.find(Employee.class, id);
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        System.out.println("JPA API working...");
        Employee targetEmployee = entityManager.merge(employee);
        //这一步不像Hibernate会自动将对象关联到session然后更新，所以要手动给传入的参数设置上id
        employee.setId(targetEmployee.getId());
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        System.out.println("JPA API working...");
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }
}
