package com.yuxuan66.dnfhelper.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxuan66.dnfhelper.service.DnfService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("dnf")
public class DnfController {

    @RequestMapping(value = "allTransRegionalGoldCoin", method = RequestMethod.GET)
    public String allTransRegionalGoldCoin() {
        Map<String, Object> map = DnfService.getGoldProportion();
        StringBuffer result = new StringBuffer("以下数据来自UU898HH");
        map.forEach((k, v) -> {
            result.append(k + "=" + v+"HH");
        });
        return result.toString();
    }
}
