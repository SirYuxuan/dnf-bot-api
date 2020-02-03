package com.yuxuan66.dnfhelper.model;

import cn.hutool.db.Entity;


public class DnfGoldCoin extends Entity {
    private Integer id;
    private Double num;
    private String createtime;

    public Integer getId() {
        return this.getInt("id");
    }

    public void setId(Integer id) {
        this.put("id", id);
    }

    public Double getNum() {
        return this.getDouble("num");
    }

    public void setNum(Double num) {
        this.put("num", num);
    }

    public String getCreatetime() {
        return this.getStr("createtime");
    }

    public void setCreatetime(String createtime) {
        this.put("createtime", createtime);
    }
}
