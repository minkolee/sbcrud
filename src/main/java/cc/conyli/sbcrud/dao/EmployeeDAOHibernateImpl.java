package cc.conyli.sbcrud.dao;

import cc.conyli.sbcrud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    //注入EntityManager
    private EntityManager entityManager;


    //EntityManager会被Spring Boot自动创建
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public List<Employee> findAll() {
        //获取Hibernate的SessionFactory
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        return currentSession.get(Employee.class, id);
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee employee = currentSession.get(Employee.class, id);
        currentSession.delete(employee);
    }
}
