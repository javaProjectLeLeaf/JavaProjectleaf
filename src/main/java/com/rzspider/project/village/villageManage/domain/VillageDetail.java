package com.rzspider.project.village.villageManage.domain;

import java.util.Date;

public class VillageDetail {
    private Long id;

    private String villageId;

    private String building;

    private String unit;

    private String floor;

    private String door;

    private Long state;

    private String phone;

    private String stateStr;

    private String ifinstallWbStr;

    private String surname;

    private String ifinstallWb;

    private String wbTypes;

    private Long wbNumber;

    private Date expirationTime;

    private String cost;

    private String mixWb;

    private Long wbTv;

    private String remarks;

    private String rangerId;

    private String rangerName;

    private Date rangerTime;

    private String threemonthAvgarpu;

    private Date nakedwbTime;

    private String ifrenewable;

    private String mixHost;

    private String mixMin;

    private String insertWay;

    private String ifwbSilent;

    private String iftvSilent;

    private String cfFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId == null ? null : villageId.trim();
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building == null ? null : building.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door == null ? null : door.trim();
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr == null ? null : stateStr.trim();
    }

    public String getIfinstallWbStr() {
        return ifinstallWbStr;
    }

    public void setIfinstallWbStr(String ifinstallWbStr) {
        this.ifinstallWbStr = ifinstallWbStr == null ? null : ifinstallWbStr.trim();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname == null ? null : surname.trim();
    }

    public String getIfinstallWb() {
        return ifinstallWb;
    }

    public void setIfinstallWb(String ifinstallWb) {
        this.ifinstallWb = ifinstallWb == null ? null : ifinstallWb.trim();
    }

    public String getWbTypes() {
        return wbTypes;
    }

    public void setWbTypes(String wbTypes) {
        this.wbTypes = wbTypes == null ? null : wbTypes.trim();
    }

    public Long getWbNumber() {
        return wbNumber;
    }

    public void setWbNumber(Long wbNumber) {
        this.wbNumber = wbNumber;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost == null ? null : cost.trim();
    }

    public String getMixWb() {
        return mixWb;
    }

    public void setMixWb(String mixWb) {
        this.mixWb = mixWb == null ? null : mixWb.trim();
    }

    public Long getWbTv() {
        return wbTv;
    }

    public void setWbTv(Long wbTv) {
        this.wbTv = wbTv;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getRangerId() {
        return rangerId;
    }

    public void setRangerId(String rangerId) {
        this.rangerId = rangerId == null ? null : rangerId.trim();
    }

    public String getRangerName() {
        return rangerName;
    }

    public void setRangerName(String rangerName) {
        this.rangerName = rangerName == null ? null : rangerName.trim();
    }

    public Date getRangerTime() {
        return rangerTime;
    }

    public void setRangerTime(Date rangerTime) {
        this.rangerTime = rangerTime;
    }

    public String getThreemonthAvgarpu() {
        return threemonthAvgarpu;
    }

    public void setThreemonthAvgarpu(String threemonthAvgarpu) {
        this.threemonthAvgarpu = threemonthAvgarpu == null ? null : threemonthAvgarpu.trim();
    }

    public Date getNakedwbTime() {
        return nakedwbTime;
    }

    public void setNakedwbTime(Date nakedwbTime) {
        this.nakedwbTime = nakedwbTime;
    }

    public String getIfrenewable() {
        return ifrenewable;
    }

    public void setIfrenewable(String ifrenewable) {
        this.ifrenewable = ifrenewable == null ? null : ifrenewable.trim();
    }

    public String getMixHost() {
        return mixHost;
    }

    public void setMixHost(String mixHost) {
        this.mixHost = mixHost == null ? null : mixHost.trim();
    }

    public String getMixMin() {
        return mixMin;
    }

    public void setMixMin(String mixMin) {
        this.mixMin = mixMin == null ? null : mixMin.trim();
    }

    public String getInsertWay() {
        return insertWay;
    }

    public void setInsertWay(String insertWay) {
        this.insertWay = insertWay == null ? null : insertWay.trim();
    }

    public String getIfwbSilent() {
        return ifwbSilent;
    }

    public void setIfwbSilent(String ifwbSilent) {
        this.ifwbSilent = ifwbSilent == null ? null : ifwbSilent.trim();
    }

    public String getIftvSilent() {
        return iftvSilent;
    }

    public void setIftvSilent(String iftvSilent) {
        this.iftvSilent = iftvSilent == null ? null : iftvSilent.trim();
    }

    public String getCfFlag() {
        return cfFlag;
    }

    public void setCfFlag(String cfFlag) {
        this.cfFlag = cfFlag == null ? null : cfFlag.trim();
    }
}