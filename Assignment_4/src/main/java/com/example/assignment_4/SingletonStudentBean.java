package com.example.assignment_4;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@Path("/highest-cgpa")
public class SingletonStudentBean {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Lock(LockType.READ)
    public String getHighestCGPA(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");
        EntityManager entityManager= emf.createEntityManager();
        Query query = entityManager.createNamedQuery("highestCGPA");
        List<Student> student_list = query.getResultList();
        Student student = student_list.get(0);
        return student.toString();
    }
}
