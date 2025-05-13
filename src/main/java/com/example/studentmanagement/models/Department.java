package com.example.studentmanagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "departments")
public class Department {

    @Id
    private String id;
    private String name;
    private String departmentCode;
    private List<Major> majors = new ArrayList<>(); 

    public Department() {}

    public Department(String name, String departmentCode, List<Major> majors) {
        this.name = name;
        this.departmentCode = departmentCode;
        this.majors = majors != null ? majors : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public List<Major> getMajors() { return majors; }
    public void setMajors(List<Major> majors) {
        this.majors = majors != null ? majors : new ArrayList<>();
    }
}
