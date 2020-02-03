package com.yuxuan66.dnfhelper.web;

import com.alibaba.fastjson.JSONArray;
import com.yuxuan66.dnfhelper.utils.DnfService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dnf")
public class DnfController {


    @RequestMapping(value = "getCrossRegionGoldProportion", method = RequestMethod.GET)
    public String getCrossRegionGoldProportion(String spanningArea) {
        return DnfService.getCrossRegionGoldProportion(spanningArea);
    }

    @RequestMapping(value = "acquiringParticles", method = RequestMethod.GET)
    public List<String> acquiringParticles(String id) {
        return DnfService.acquiringParticles(id);
    }

    @RequestMapping(value = "generateExamples", method = RequestMethod.GET)
    public String generateExamples(String text) {
        return DnfService.generateExamples(text);
    }

    @RequestMapping(value = "acquisitionTree", method = RequestMethod.GET)
    public Map<String, Object> acquisitionTree(String id) {
        return DnfService.acquisitionTree(id);
    }

    @RequestMapping(value = "generateTree", method = RequestMethod.GET)
    public String generateTree(String text1, String text2, String text3) {
        return DnfService.generateTree(text1, text2, text3);
    }


    @RequestMapping(value = "allTransRegionalGoldCoin", method = RequestMethod.GET)
    public String allTransRegionalGoldCoin() {
        Map<String, Object> map = DnfService.getGoldProportion();
        StringBuffer result = new StringBuffer("以下数据来自UU898HH");
        map.forEach((k, v) -> {
            result.append("金币:" + k + "=" + v + "HH");
        });
        Map<String, Object> map1 = DnfService.getContradiction();
        map1.forEach((k, v) -> {
            result.append("矛盾:" + k + "=" + v + "HH");
        });
        return result.toString();
    }

    @RequestMapping(value = "addMsg", method = RequestMethod.POST)
    public void addMsg(String group, String qq, String nickName, String name, String groupName, String msg) {
        try {
            DnfService.addMsg(group, qq, nickName, name, groupName, msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "getHistory", method = RequestMethod.GET)
    public String getHistory(String time) {
        try {
            return DnfService.getHistory(time);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "无法获取当日金币比例,如有疑问 请联系狗哥";
    }

    @RequestMapping(value = "getTheNumberOfSpeeches", method = RequestMethod.GET)
    public String getTheNumberOfSpeeches(String group) {
        try {
            return DnfService.getTheNumberOfSpeeches(group);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "无法获取当日数据,如有疑问 请联系狗哥";
    }

    @RequestMapping(value = "getMsg", method = RequestMethod.GET)
    public String getMsg(String group, String qq, String startTime, String endTime) {
        try {
            return DnfService.getMsg(group, qq, startTime, endTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "无法获取数据,如有疑问 请联系狗哥";
    }

    @RequestMapping(value = "getTrendChart", method = RequestMethod.GET)
    public JSONArray getTrendChart(String time) {
        try {
            return DnfService.getTrendChart(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
