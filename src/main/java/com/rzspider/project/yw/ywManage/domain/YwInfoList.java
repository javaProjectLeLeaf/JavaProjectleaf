package com.rzspider.project.yw.ywManage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.yw.ywManage.mapper.YwInfoListMapper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class YwInfoList extends BaseEntity {
    private String create_datestr;

    private String update_datestr;

    private String delete_datestr;

    private String restore_datestr;
    private Integer id;

    private String yw_title;

    private String yw_type;

    private String yw_dtl;

    private String yw_content;

    private Integer op_id;

    private String staff_name;

    private Integer bill_id;

    private Date create_date;

    private Date update_date;

    private Date delete_date;

    private Date restore_date;

    private Integer status;

    private String fee_file;

    private String exfile;

    private String ex1;

    private String ex2;

    private Integer ex3;

    private Integer ex4;

    private String ex5;

    public String getCreate_datestr() {
        return create_datestr;
    }

    public void setCreate_datestr(String create_datestr) {
        this.create_datestr = create_datestr;
    }

    public String getUpdate_datestr() {
        return update_datestr;
    }

    public void setUpdate_datestr(String update_datestr) {
        this.update_datestr = update_datestr;
    }

    public String getDelete_datestr() {
        return delete_datestr;
    }

    public void setDelete_datestr(String delete_datestr) {
        this.delete_datestr = delete_datestr;
    }

    public String getRestore_datestr() {
        return restore_datestr;
    }

    public void setRestore_datestr(String restore_datestr) {
        this.restore_datestr = restore_datestr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYw_title() {
        return yw_title;
    }

    public void setYw_title(String yw_title) {
        this.yw_title = yw_title == null ? null : yw_title.trim();
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

    public String getYw_content() {
        return yw_content;
    }

    public void setYw_content(String yw_content) {
        this.yw_content = yw_content == null ? null : yw_content.trim();
    }

    public Integer getOp_id() {
        return op_id;
    }

    public void setOp_id(Integer op_id) {
        this.op_id = op_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name == null ? null : staff_name.trim();
    }

    public Integer getBill_id() {
        return bill_id;
    }

    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Date getDelete_date() {
        return delete_date;
    }

    public void setDelete_date(Date delete_date) {
        this.delete_date = delete_date;
    }

    public Date getRestore_date() {
        return restore_date;
    }

    public void setRestore_date(Date restore_date) {
        this.restore_date = restore_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFee_file() {
        return fee_file;
    }

    public void setFee_file(String fee_file) {
        this.fee_file = fee_file == null ? null : fee_file.trim();
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