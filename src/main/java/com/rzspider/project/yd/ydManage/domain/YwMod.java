package com.rzspider.project.yd.ydManage.domain;

public class YwMod {
    private Integer id;

    private String ywType;

    private String ywDtl;

    private String ywComment;

    private String ex1;

    private String ex2;

    private Integer ex3;

    private Integer page;

    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYwType() {
        return ywType;
    }

    public void setYwType(String ywType) {
        this.ywType = ywType == null ? null : ywType.trim();
    }

    public String getYwDtl() {
        return ywDtl;
    }

    public void setYwDtl(String ywDtl) {
        this.ywDtl = ywDtl == null ? null : ywDtl.trim();
    }

    public String getYwComment() {
        return ywComment;
    }

    public void setYwComment(String ywComment) {
        this.ywComment = ywComment == null ? null : ywComment.trim();
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