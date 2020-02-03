package com.yuxuan66.dnfhelper.task;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import com.yuxuan66.dnfhelper.model.DnfGoldCoin;
import com.yuxuan66.dnfhelper.utils.DnfService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Configuration
@EnableScheduling
public class ScheduleTask {
    Db db = Db.use();

    @Scheduled(fixedRate = 1000 * 60 * 3)
    private void configureTasks() {
        try {
            DnfService.getGoldProportion().forEach((key, value) -> {
                DnfGoldCoin dnfGoldCoin = new DnfGoldCoin();
                dnfGoldCoin.setNum(Convert.toDouble(value));
                dnfGoldCoin.setCreatetime(DateUtil.now());
                dnfGoldCoin.setTableName("dnf_gold_coin");
                try {
                    db.insert(dnfGoldCoin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {

        }
    }

}
