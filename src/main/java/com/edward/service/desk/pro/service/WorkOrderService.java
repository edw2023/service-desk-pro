package com.edward.service.desk.pro.service;

import com.edward.service.desk.pro.pojo.WorkOrder;
import com.edward.service.desk.pro.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderService {

    @Autowired
    private EmailService emailService; // 假设您使用电子邮件通知

    @Autowired
    private WorkOrderRepository workOrderRepository;

    // 其他依赖和方法...

    public void sendNotificationsForPendingWorkOrders() {
        // 查询所有待处理工单
        List<WorkOrder> pendingWorkOrders = workOrderRepository.findByStatus("待处理");

        for (WorkOrder workOrder : pendingWorkOrders) {
            // 获取工单相关的员工和支持团队信息
            String assigneeEmail = workOrder.getAssignedTo(); // 假设这是处理人的电子邮件
            String requesterEmail = workOrder.getRequesterEmail(); // 提交工单的员工的电子邮件

            // 发送电子邮件通知给处理人
            String subject = "待处理工单提醒";
            String message = "您有一个待处理工单，请尽快处理。";
            emailService.sendEmail(assigneeEmail, subject, message);

            // 发送电子邮件通知给提交工单的员工
            String requesterSubject = "工单进展通知";
            String requesterMessage = "您提交的工单正在处理中，请关注进展。";
            emailService.sendEmail(requesterEmail, requesterSubject, requesterMessage);

            // 更新工单的通知状态，以防止重复通知
            workOrder.setProcessorNotified(true);
            workOrderRepository.save(workOrder);
        }
    }

    // 添加其他服务方法...
}

