package com.yuxuan66.dnfhelper.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuxuan66.dnfhelper.model.DnfMsg;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class DnfService {
    static Db db = Db.use();

    public static String generateExamples(String text) {
        try {
            long id = db.executeForGeneratedKey("insert into lizi values(null,?)", text);
            return String.valueOf(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateTree(String text1, String text2, String text3) {
        String text = text1;
        text1 = text2.split(",")[0];
        text2 = text2.split(",")[text2.split(",").length - 1];
        Calendar now = Calendar.getInstance();
        try {
            now = DateUtil.parseDate(text3).toCalendar();
        } catch (Exception e) {

        }
        String nian = now.get(Calendar.YEAR) + "";
        String yue = (now.get(Calendar.MONTH) + 1) + "";
        String ri = now.get(Calendar.DAY_OF_MONTH) + "";
        String shi = now.get(Calendar.HOUR_OF_DAY) + "";
        String fen = now.get(Calendar.MINUTE) + "";
        String miao = now.get(Calendar.SECOND) + "";

        try {
            long id = db.executeForGeneratedKey("insert into tree values(null,?,?,?,?,?,?,?,?,?)", text, text1, text2, nian, yue, ri, shi, fen, miao);
            return String.valueOf(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Map<String, Object> acquisitionTree(String id) {
        try {
            return db.queryOne("select * from tree where id = ?", id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> acquiringParticles(String id) {
        try {
            return Arrays.asList(db.query("select text from lizi where id = ?", String.class, id).get(0).split(","));
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    static String[] spanOne = {"http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2322&s=24986&c=-3&cmp=-1&_t={}"
            , "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2326&s=25010&c=-3&cmp=-1&_t={}"
            , "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2324&s=25054&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2342&s=25049&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2331&s=25021&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2331&s=25018&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2330&s=25094&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2335&s=25081&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2330&s=25095&c=-3&cmp=-1&_t={}"};

    /**
     * 获取指定跨区金币比例
     *
     * @param spanningArea
     * @return
     */
    public static String getCrossRegionGoldProportion(String spanningArea) {

        try {
            String url = "";
            switch (spanningArea.toUpperCase()) {
                case "跨一":
                    url = spanOne[0];
                    break;
                case "跨二":
                    url = spanOne[1];
                    break;
                case "跨三A":
                    url = spanOne[2];
                    break;
                case "跨三B":
                    url = spanOne[3];
                    break;
                case "跨四":
                    url = spanOne[4];
                    break;
                case "跨五":
                    url = spanOne[5];
                    break;
                case "跨六":
                    url = spanOne[6];
                    break;
                case "跨七":
                    url = spanOne[7];
                    break;
                case "跨八":
                    url = spanOne[8];
                    break;
                default:
                    url = "NO";
                    break;
            }
            if ("NO".equals(url)) {
                return "无法获取此跨区数据";
            }
            String jsonText = HttpUtil.get(StrUtil.format(url, System.currentTimeMillis()));
            JSONObject webJSON = JSONObject.parseObject(jsonText);
            JSONArray jsonArray = webJSON.getJSONObject("list").getJSONArray("datas");
            BigDecimal money = BigDecimal.ZERO;
            for (Object item : jsonArray) {
                String tmp = JSONObject.toJSONString(item);
                money = money.add(JSONObject.parseObject(tmp).getBigDecimal("Scale"));
            }
            money = money.divide(new BigDecimal(String.valueOf(jsonArray.size()))).setScale(2, BigDecimal.ROUND_HALF_UP);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String crossPrice = decimalFormat.format(money);
            return spanningArea.toUpperCase() + "金币比例:" + crossPrice;
        } catch (Exception e) {
            return "无法获取此跨区数据";
        }

    }


    /**
     * 添加一条记录到数据库
     *
     * @param group
     * @param qq
     * @param nickName
     * @param name
     * @param groupName
     * @param msg
     * @throws SQLException
     */
    public static void addMsg(String group, String qq, String nickName, String name, String groupName, String msg) throws SQLException {
        DnfMsg dnfMsg = new DnfMsg();
        dnfMsg.setFromQQ(qq);
        dnfMsg.setFromGroup(group);
        dnfMsg.setCreatetime(DateUtil.now());
        dnfMsg.setGroupName(groupName);
        dnfMsg.setMsg(msg);
        dnfMsg.setNickName(nickName);
        dnfMsg.setName(name);
        dnfMsg.setTableName("dnf_msg");
        db.insert(dnfMsg);
    }

    public static String getTheNumberOfSpeeches(String group) throws SQLException {
        String startTime = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        String endTime = DateUtil.format(new Date(), "yyyy-MM-dd 23:59:59");
        List<Entity> entities = db.query("select fromQQ as QQ,count(1) as 发言次数 from dnf_msg where fromGroup = ? and createtime >= ? and createtime <= ?  GROUP BY fromQQ ORDER BY 发言次数 desc limit 3", group, startTime, endTime);
        StringBuilder stringBuilder = new StringBuilder();
        entities.forEach(entity -> {
            stringBuilder.append(entity.getStr("QQ") + "," + entity.getInt("发言次数") + "|");
        });
        String a = stringBuilder.toString();
        a = a.substring(0, a.length() - 1);
        return a;
    }

    public static String getMsg(String group, String qq, String startTime, String endTime) throws SQLException {
        List<DnfMsg> entities = db.query("SELECT * FROM dnf_msg WHERE fromGroup = ? and fromQQ = ? and createtime >= ? and createtime <= ?", DnfMsg.class, group, qq, startTime, endTime);
        StringBuilder stringBuilder = new StringBuilder();
        entities.forEach(entity -> {
            stringBuilder.append("昵称:" + entity.getNickName() + "  名片:" + entity.getName() + " 时间:" + entity.getCreatetime() + " HH " + entity.getMsg() + " HH ");
        });
        return stringBuilder.toString();
    }

    public static JSONArray getTrendChart(String time) throws SQLException {
        if (StrUtil.isBlank(time)) {
            time = DateUtil.format(new Date(), "yyyy-MM-dd");
        }
        String endTime = DateUtil.parse(time, "yyyy-MM-dd").offset(DateField.HOUR, 24).toString("yyyy-MM-dd");
        List<Entity> entity = db.query("SELECT createtime as time,num as num FROM dnf_gold_coin WHERE createtime > ? and createtime < ?", time, endTime);
        entity.forEach(entity1 -> {
            entity1.put("time", DateUtil.parse(entity1.getStr("time")).toString("HH:mm:ss"));
        });
        return JSONArray.parseArray(JSONArray.toJSONString(entity));
    }

    public static String getHistory(String time) throws SQLException {
        try {
            String endTime = DateUtil.parse(time, "yyyy-MM-dd").offset(DateField.HOUR, 24).toString("yyyy-MM-dd");
            Entity entity = db.queryOne("SELECT (CASE count(1) when 0 then 0 else SUM(num)/count(1) end) as a FROM dnf_gold_coin WHERE createtime > ? and createtime < ?", time, endTime);
            if (entity == null) {
                return "无法获取当日价格";
            }
            String ss = NumberUtil.roundStr(Convert.toDouble(entity.getStr("a"), 0d), 2);
            return "当日金币比例:" + ("0.00".equals(ss) ? "无统计数据" : ss);
        } catch (Exception e) {

        }
        return "日期格式错误 请参考:" + DateUtil.format(new Date(), "yyyy-MM-dd");
    }

    public static Map<String, Object> getGoldProportion() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        String url = StrUtil.format(spanOne[6], System.currentTimeMillis());
        String jsonText = HttpUtil.get(url);
        JSONObject webJSON = JSONObject.parseObject(jsonText);
        JSONArray jsonArray = webJSON.getJSONObject("list").getJSONArray("datas");
        BigDecimal money = BigDecimal.ZERO;
        for (Object item : jsonArray) {
            String tmp = JSONObject.toJSONString(item);
            money = money.add(JSONObject.parseObject(tmp).getBigDecimal("Scale"));
        }
        money = money.divide(new BigDecimal(String.valueOf(jsonArray.size()))).setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String crossPrice = decimalFormat.format(money);
        result.put("跨六", crossPrice);
        return result;
    }

    public static Map<String, Object> getContradiction() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        String url = StrUtil.format("http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2330&s=25094&c=838&cmp=-1&_t={}", System.currentTimeMillis());
        String jsonText = HttpUtil.get(url);
        JSONObject webJSON = JSONObject.parseObject(jsonText);
        JSONArray jsonArray = webJSON.getJSONObject("list").getJSONArray("datas");
        BigDecimal money = BigDecimal.ZERO;
        for (Object item : jsonArray) {
            String tmp = JSONObject.toJSONString(item);
            money = money.add(JSONObject.parseObject(tmp).getBigDecimal("Scale"));
        }
        money = money.divide(new BigDecimal(String.valueOf(jsonArray.size()))).setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String crossPrice = decimalFormat.format(money);
        result.put("跨六", crossPrice);
        return result;
    }


}
