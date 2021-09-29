package com.rzspider.project.yw.ywManage.domain;

public class YwModule {
    private Integer id;

    private String yw_type;

    private String yw_dtl;

    private String yw_comment;

    private String ex1;

    private String ex2;

    private Integer ex3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYw_type() {
        return yw_type;
    }

    public void setYw_type(String yw_type) {
        this.yw_type = yw_type == null ? null : yw_type.trim();
    }

    public String getYw_dtl() {
        return yw_dtl;
    }

    public void setYw_dtl(String yw_dtl) {
        this.yw_dtl = yw_dtl == null ? null : yw_dtl.trim();
    }

    public String getYw_comment() {
        return yw_comment;
    }

    public void setYw_comment(String yw_comment) {
        this.yw_comment = yw_comment == null ? null : yw_comment.trim();
    }

    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1 == null ? null : ex1.trim();
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2 == null ? null : ex2.trim();
    }

    public Integer getEx3() {
        return ex3;
    }

    public void setEx3(Integer ex3) {
        this.ex3 = ex3;
    }
}