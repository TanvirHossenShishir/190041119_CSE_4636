package com.example.assignment_4;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/student")
public class StudentBean {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void addStudent(@QueryParam("name") String name,
                           @QueryParam("id") int sid,
                           @QueryParam("semester") String sem,
                           @QueryParam("cgpa") double cgpa){

        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Student student = new Student();
        student.setId(sid);
        student.setName(name);
        student.setSection(sem);
        student.setCgpa(cgpa);

        entityManager.persist(student);
        entityTransaction.commit();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getName(@PathParam("id") int sid){
        return getStudentById(sid).getName();
    }

    @GET
    @Path("/higher-cgpa")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestCGPA(@QueryParam("id1") int id1,
                               @QueryParam("id2") int id2 ){
        Student student1 = getStudentById(id1);
        Student student2 = getStudentById(id2);
        if (student1.getCgpa() >= student2.getCgpa()) {
            return student1.toString();
        } else {
            return student2.toString();
        }
    }

    @GET
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateName(@QueryParam("id") int sid,
                              @QueryParam("name") String name){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Student student = entityManager.find(Student.class, sid);
        student.setName(name);
        entityTransaction.commit();
    }

    private Student getStudentById(int id) {
        EntityManager entityManager= emf.createEntityManager();
        TypedQuery<Student> query = entityManager.createNamedQuery("getStudent", Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
