package com.edward.service.desk.pro.scheduler;

import com.edward.service.desk.pro.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private WorkOrderService workOrderService;

    @Scheduled(cron = "0 0 8 * * ?") // 每天早上8点触发一次通知
    public void sendNotifications() {
        // 检查工单状态并发送通知
        workOrderService.sendNotificationsForPendingWorkOrders();
    }

    // 添加其他定时任务方法
}

