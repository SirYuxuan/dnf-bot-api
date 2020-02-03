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
package com.yuxuan66.dnfhelper.web;

import com.yuxuan66.dnfhelper.utils.DnfService;
import com.yuxuan66.dnfhelper.utils.PneumoniaUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("pneumonia")
public class PneumoniaController {


    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String get(String city) {
        try {
            return PneumoniaUtil.get(city);
        } catch (IOException e) {
            return "程序错误 请联系雨轩";
        }
    }
}
