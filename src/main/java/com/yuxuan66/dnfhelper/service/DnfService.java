package com.yuxuan66.dnfhelper.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class DnfService {
    static String[] spanOne = {"http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2322&s=24986&c=-3&cmp=-1&_t={}"
            , "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2326&s=25010&c=-3&cmp=-1&_t={}"
            , "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2324&s=25054&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2342&s=25049&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2331&s=25021&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2331&s=25018&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2330&s=25094&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2335&s=25081&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2346&s=25088&c=-3&cmp=-1&_t={}",
            "http://www.uu898.com/ashx/GameRetail.ashx?act=a001&g=95&a=2330&s=25095&c=-3&cmp=-1&_t={}"};

    public static Map<String,Object> getGoldProportion() {
        LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
        for (int i = 0; i < spanOne.length; i++) {
            String url = StrUtil.format(spanOne[i], System.currentTimeMillis());
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
            switch (i){
                case 0:
                    result.put("跨一", crossPrice);
                    break;
                case 1:
                    result.put("跨二", crossPrice);
                    break;
                case 2:
                    result.put("跨三A", crossPrice);
                    break;
                case 3:
                    result.put("跨三B", crossPrice);
                    break;
                case 4:
                    result.put("跨四", crossPrice);
                    break;
                case 5:
                    result.put("跨五", crossPrice);
                    break;
                case 6:
                    result.put("跨六", crossPrice);
                    break;
                case 7:
                    result.put("跨七", crossPrice);
                    break;
                case 8:

                    break;
                case 9:
                    result.put("跨八", crossPrice);
                    break;
            }
        }
        return result;
    }


}
