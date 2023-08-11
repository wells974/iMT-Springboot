package com.oddfar.campus.business.task;

import com.oddfar.campus.business.mapper.IShopMapper;
import com.oddfar.campus.business.service.IMTService;
import com.oddfar.campus.business.service.IShopService;
import com.oddfar.campus.business.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * i茅台定时任务
 */
@Configuration
@EnableScheduling
public class CampusIMTTask {
    private static final Logger logger = LoggerFactory.getLogger(CampusIMTTask.class);

    @Autowired
    private IMTService imtService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IShopService iShopService;


    /**
     * 1：10 批量修改用户随机预约的时间
     */
    @Async
    @Scheduled(cron = "0 10 1 ? * * ")
    public void updateUserMinuteBatch() {
        iUserService.updateUserMinuteBatch();
    }


    /**
     * 11点期间，每分钟执行一次批量获得旅行奖励
     */
    @Async
    @Scheduled(cron = "0 0/1 11 ? * *")
    public void getTravelRewardBatch() {
        imtService.getTravelRewardBatch();

    }

    /**
     * 9点期间，每分钟执行一次，预约茅台
     */
    @Async
    @Scheduled(cron = "0 0/1 9 ? * *")
    public void reservationBatchTask() {
        imtService.reservationBatch();

    }


    /**
     *  8:10 8:55 刷新商品、版本号、门店数据
     */
    @Async
    @Scheduled(cron = "0 10,55 8 ? * * ")
    public void refresh() {
        logger.info("「刷新数据」开始刷新版本号，预约item，门店shop列表  ");
        try {
            imtService.refreshAll();
        } catch (Exception e) {
            logger.info("「刷新数据执行报错」%s", e.getMessage());
        }

    }


    /**
     * 18.05分获取申购结果
     */
    @Async
    @Scheduled(cron = "0 5 18 ? * * ")
    public void appointmentResults() {
        imtService.appointmentResults();
    }


}