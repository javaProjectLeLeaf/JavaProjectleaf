package com.rzspider.project.wage.wageManage.domain;

public class Wage {
    private Integer id;

    private String name;

    private String department;

    private String wage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage == null ? null : wage.trim();
    }
}