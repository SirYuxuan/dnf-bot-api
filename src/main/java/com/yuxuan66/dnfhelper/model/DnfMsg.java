package com.yuxuan66.dnfhelper.model;

import cn.hutool.db.Entity;

public class DnfMsg extends Entity {

    private Long id;
    private String fromGroup;
    private String fromQQ;
    private String msg;
    private String nickName;
    private String name;
    private String createtime;
    private String groupName;

    public Long getId() {
        return this.getLong("id");
    }

    public void setId(Long id) {
        this.put("id", id);
    }

    public String getFromGroup() {
        return this.getStr("fromGroup");
    }

    public void setFromGroup(String fromGroup) {
        this.put("fromGroup", fromGroup);
    }

    public String getFromQQ() {
        return this.getStr("fromQQ");
    }

    public void setFromQQ(String fromQQ) {
        this.put("fromQQ", fromQQ);
    }

    public String getMsg() {
        return this.getStr("msg");
    }

    public void setMsg(String msg) {
        this.put("msg", msg);
    }

    public String getNickName() {
        return this.getStr("nickName");
    }

    public void setNickName(String nickName) {
        this.put("nickName", nickName);
    }

    public String getName() {
        return this.getStr("name");
    }

    public void setName(String name) {
        this.put("name", name);
    }

    public String getCreatetime() {
        return this.getStr("createtime");
    }

    public void setCreatetime(String createtime) {
        this.put("createtime", createtime);
    }

    public String getGroupName() {
        return this.getStr("groupName");
    }

    public void setGroupName(String groupName) {
        this.put("groupName", groupName);
    }
}
