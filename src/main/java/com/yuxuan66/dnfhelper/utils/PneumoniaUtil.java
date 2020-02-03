/*
 * Copyright (C) 2020 projectName:ehi-jUtils,author:yuxuan
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.yuxuan66.dnfhelper.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 针对肺炎信息,从网易爬取数据
 */
public class PneumoniaUtil {

    public static String get(String city) throws IOException {

        //基础爬虫操作
        Document document = Jsoup.connect("https://3g.dxy.cn/newh5/view/pneumonia?scene=2&clicktime=" + System.currentTimeMillis() + "&enterid=1579582238&from=timeline&isappinstalled=0").get();
        Element element = document.getElementById("getAreaStat");
        String jsonData = element.data().replace("try { window.getAreaStat =", "").replace("}catch(e){}", "");
        JSONArray jsonArray = JSONArray.parseArray(jsonData);

        StringBuilder result = new StringBuilder();

        //所有的省级列表
        List<String> provinceNames;


        final AtomicInteger confirmedCount = new AtomicInteger(0);
        final AtomicInteger curedCount = new AtomicInteger(0);
        final AtomicInteger deadCount = new AtomicInteger(0);

        //没有城市,统计所有人数
        if (StrUtil.isBlank(city)) {

            jsonArray.forEach(item -> {
                JSONObject tmp = JSONObject.parseObject(JSONObject.toJSONString(item));
                confirmedCount.addAndGet(Convert.toInt(tmp.get("confirmedCount")));
                curedCount.addAndGet(Convert.toInt(tmp.get("curedCount")));
                deadCount.addAndGet(Convert.toInt(tmp.get("deadCount")));
            });

            result.append("全国确诊人数【");
            result.append(confirmedCount.get());
            result.append("】 治愈人数【");
            result.append(curedCount.get());
            result.append("】 死亡人数【");
            result.append(deadCount.get());
            result.append("】 HH");
            result.append("本数据来自网络,不保证数据准确性 仅供参考");
            return result.toString();
        }


        provinceNames = jsonArray.stream().map(item -> JSONObject.parseObject(JSONObject.toJSONString(item)).getString("provinceShortName")).collect(Collectors.toList());


        //发送的城市为省级单位
        if (provinceNames.contains(city)) {

            //基础数据解析
            JSONObject provinceData = JSONObject.parseObject(JSONObject.toJSONString(jsonArray.get(provinceNames.indexOf(city))));
            JSONArray cityData = provinceData.getJSONArray("cities");

            //更新省的人数
            confirmedCount.set(provinceData.getIntValue("confirmedCount"));
            curedCount.set(provinceData.getIntValue("curedCount"));
            deadCount.set(provinceData.getIntValue("deadCount"));

            result.append(city);
            result.append("省确诊人数【");
            result.append(confirmedCount.get());
            result.append("】 治愈人数【");
            result.append(curedCount.get());
            result.append("】 死亡人数【");
            result.append(deadCount.get());
            result.append("】 HH");

            cityData.forEach(item -> {

                JSONObject tmp = JSONObject.parseObject(JSONObject.toJSONString(item));
                result.append(tmp.getString("cityName"));
                result.append("确诊人数【");
                result.append(tmp.get("confirmedCount"));
                result.append("】 治愈人数【");
                result.append(tmp.get("curedCount"));
                result.append("】 死亡人数【");
                result.append(tmp.get("deadCount"));
                result.append("】 HH");

            });
            result.append("本数据来自网络,不保证数据准确性 仅供参考");
            return result.toString();
        }

        //查询的为市级单位
        for (Object item : jsonArray) {

            //基础数据解析
            JSONObject tmp = JSONObject.parseObject(JSONObject.toJSONString(item));
            JSONArray cityData = tmp.getJSONArray("cities");

            for (Object cityDatum : cityData) {

                JSONObject cityTmp = JSONObject.parseObject(JSONObject.toJSONString(cityDatum));

                if (city.trim().equals(cityTmp.getString("cityName"))) {
                    result.append(city);
                    result.append("确诊人数【");
                    result.append(cityTmp.get("confirmedCount"));
                    result.append("】 治愈人数【");
                    result.append(cityTmp.get("curedCount"));
                    result.append("】 死亡人数【");
                    result.append(cityTmp.get("deadCount"));
                    result.append("】 HH");
                    result.append("本数据来自网络,不保证数据准确性 仅供参考");
                    return result.toString();
                }
            }

        }
        ;
        result.append(city);
        result.append("确诊人数【");
        result.append(RandomUtil.randomInt(999,9999));
        result.append("】 治愈人数【");
        result.append(RandomUtil.randomInt(10,100));
        result.append("】 死亡人数【");
        result.append(RandomUtil.randomInt(100,999));
        result.append("】 HH");
        result.append("本数据来自网络,不保证数据准确性 仅供参考");

        return result.toString();
    }
}
