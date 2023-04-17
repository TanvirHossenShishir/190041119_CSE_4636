package com.example.assignment_4;

import javax.persistence.*;

@Entity
@Table(name="student")
@NamedQuery(name = "getStudent",
        query = "Select s from Student  s where s.id = :id")
@NamedQuery(name = "highestCGPA",
        query = "Select s from Student  s where s.cgpa = (select max(cgpa) from Student )")
public class Student {
    @Id
    @Column(name = "id")
    private  int id;
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    @Column(name = "semester", nullable = false, length = 150)
    private String semester;
    @Column(name = "cgpa")
    private double cgpa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSection(String section) {
        this.semester = section;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Section='" + semester + '\'' +
                ", Cgpa='" + cgpa +'\''+
                '}';
    }
}
