package com.edward.service.desk.pro.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;
    private String assignedTo;
    private List<String> history = new ArrayList<>();

    private String createdBy; // 工单创建人
    private LocalDateTime creationTimestamp; // 工单创建时间
    private LocalDateTime lastModifiedTimestamp; // 最后修改时间
    private String resolution; // 解决方案
    private LocalDateTime resolvedTimestamp; // 工单解决时间
    private String attachmentUrl; // 附件链接
    private String requesterName; // 提交工单的员工姓名
    private String requesterEmail; // 提交工单的员工电子邮件
    private String contactPhoneNumber; // 联系电话号码
    private String relatedSystem; // 相关系统或模块
    private String assigneeNotes; // 分配给处理人的备注
    private boolean urgent; // 是否紧急工单标志
    private boolean escalated; // 是否升级工单标志

    private String workOrderType; // 工单类型
    private String progress; // 工单进度
    private String processingAgent; // 处理人
    private String approvalStatus; // 审批状态
    private String relatedWorkOrder; // 关联工单
    private List<String> labels = new ArrayList<>(); // 工单标签
    private List<String> comments = new ArrayList<>(); // 工单评论
    private double processingTime; // 工单处理时长（以小时为单位）
    List<Attachment> attachments;//工单附件
    private boolean isProcessorNotified;

    private String issueCause; // 问题原因
    private String issueFrequency; // 问题频率
    private String solution; // 解决办法
    private List<String> issueTags = new ArrayList<>(); // 问题标签
    private List<String> processingLog = new ArrayList<>(); // 处理日志
    private String severity; // 问题严重程度
    private String issueStatus; // 问题状态
    private LocalDateTime issueSubmissionTime; // 问题提出时间

    // 其他属性、Getter/Setter 省略
}
