package com.rzspider.project.yd.ydManage.domain;

import com.rzspider.framework.web.domain.BaseEntity;

import java.util.Date;

public class YwInfo {
    private Integer id;

    private String ywTitle;

    private String ywType;

    private String ywDtl;

    private String ywContent;

    private Integer opId;

    private String staffName;

    private String billId;

    private Date createDate;

    private Date updateDate;

    private Date deleteDate;

    private Date restoreDate;

    private Integer status;

    private String feeFile;

    private String exfile;

    private String ex1;

    private String ex2;

    private Integer ex3;

    private Integer ex4;

    private String ex5;

    private Integer page;

    private Integer rows;

    private String createDateStr;

    private String updateDateStr;

    private String deleteDateStr;

    private String restoreDateStr;

    private String startStr;

    private String endStr;

    private Date start;

    private Date end;

    public String getStartStr() {
        return startStr;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getDeleteDateStr() {
        return deleteDateStr;
    }

    public void setDeleteDateStr(String deleteDateStr) {
        this.deleteDateStr = deleteDateStr;
    }

    public String getRestoreDateStr() {
        return restoreDateStr;
    }

    public void setRestoreDateStr(String restoreDateStr) {
        this.restoreDateStr = restoreDateStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYwTitle() {
        return ywTitle;
    }

    public void setYwTitle(String ywTitle) {
        this.ywTitle = ywTitle == null ? null : ywTitle.trim();
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

    public String getYwContent() {
        return ywContent;
    }

    public void setYwContent(String ywContent) {
        this.ywContent = ywContent == null ? null : ywContent.trim();
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getRestoreDate() {
        return restoreDate;
    }

    public void setRestoreDate(Date restoreDate) {
        this.restoreDate = restoreDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeeFile() {
        return feeFile;
    }

    public void setFeeFile(String feeFile) {
        this.feeFile = feeFile == null ? null : feeFile.trim();
    }

    public String getExfile() {
        return exfile;
    }

    public void setExfile(String exfile) {
        this.exfile = exfile == null ? null : exfile.trim();
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

    public Integer getEx4() {
        return ex4;
    }

    public void setEx4(Integer ex4) {
        this.ex4 = ex4;
    }

    public String getEx5() {
        return ex5;
    }

    public void setEx5(String ex5) {
        this.ex5 = ex5 == null ? null : ex5.trim();
    }
}