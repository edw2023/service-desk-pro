package com.edward.service.desk.pro.controller;

import com.edward.service.desk.pro.pojo.Attachment;
import com.edward.service.desk.pro.pojo.WorkOrder;
import com.edward.service.desk.pro.repository.AttachmentRepository;
import com.edward.service.desk.pro.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workorders")
public class WorkOrderController {
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;

    // 创建工单
    @PostMapping("/create")
    public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrder workOrderRequest) {
        try {
            // 从请求中获取工单信息
            String category = workOrderRequest.getCategory();
            String title = workOrderRequest.getTitle();
            String description = workOrderRequest.getDescription();
            String priority = workOrderRequest.getPriority();
            // 可以根据需要获取其他字段

            // 检查工单信息的有效性
            if (category == null || title == null || description == null || priority == null) {
                return ResponseEntity.badRequest().body("工单信息不完整");
            }

            // 在实际应用中，根据规则分配工单给支持团队或个人
            String assignedTo = assignWorkOrder(category);

            // 创建工单对象
            WorkOrder workOrder = new WorkOrder();
            workOrder.setCategory(category);
            workOrder.setTitle(title);
            workOrder.setDescription(description);
            workOrder.setPriority(priority);
            workOrder.setStatus("待处理"); // 设置初始状态为待处理
            workOrder.setAssignedTo(assignedTo); // 分配工单给指定支持团队或个人

            // 可以根据需要设置其他字段，例如提交人、创建时间等

            // 保存工单到数据库
            WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);

            // 返回成功响应
            return ResponseEntity.ok(savedWorkOrder);
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("创建工单时出错：" + e.getMessage());
        }
    }

    // 根据规则分配工单
    private String assignWorkOrder(String category) {
        // 在实际应用中，根据问题类别和其他规则来确定分配给哪个支持团队或个人
        // 这里简单地示范一个规则，实际情况可能更复杂
        if ("销售管理系统".equalsIgnoreCase(category)) {
            // 根据问题类别分配到销售管理支持团队
            return "SalesSupportTeam";
        } else {
            // 其他情况分配到默认支持团队或个人
            return "DefaultSupportTeam";
        }
    }


    // 获取工单列表
    @GetMapping("/list")
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return ResponseEntity.ok(workOrders);
    }

    // 获取工单状态
    @GetMapping("/{id}/status")
    public ResponseEntity<String> getWorkOrderStatus(@PathVariable Long id) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            String status = optionalWorkOrder.get().getStatus();
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据工单ID获取工单详情
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkOrderById(@PathVariable Long id) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();
            return ResponseEntity.ok(workOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 更新工单状态
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder updatedWorkOrder) {
        try {
            Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
            if (optionalWorkOrder.isPresent()) {
                WorkOrder workOrder = optionalWorkOrder.get();
                // 更新工单信息
                // ...

                // 保存更新后的工单信息
                WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);
                return ResponseEntity.ok(savedWorkOrder);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新工单信息时出错：" + e.getMessage());
        }
    }

    // 分配工单给支持团队或个人
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignWorkOrder(@PathVariable Long id, @RequestBody String assignedTo) {
        try {
            Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
            if (optionalWorkOrder.isPresent()) {
                WorkOrder workOrder = optionalWorkOrder.get();
                workOrder.setAssignedTo(assignedTo);
                // 更新工单分配信息
                // ...

                // 保存更新后的工单信息
                WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);
                return ResponseEntity.ok(savedWorkOrder);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("分配工单时出错：" + e.getMessage());
        }
    }

    // 发送通知消息给相关团队或员工，可以使用邮件、消息队列等方式通知
    @PutMapping("/{id}/notify")
    public ResponseEntity<?> sendNotification(@PathVariable Long id, @RequestBody String message) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();
            String assignedTo = workOrder.getAssignedTo(); // 获取负责人
            // 发送通知消息给相关团队或员工，可以使用邮件、消息队列等方式通知
            // ...

            return ResponseEntity.ok("通知已发送");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //工单附件管理
    // 工单附件管理
    @PostMapping("/{id}/attachments")
    public ResponseEntity<?> uploadAttachment(@PathVariable Long id, @RequestParam MultipartFile file) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();

            // 保存附件信息
            Attachment attachment = saveAttachment(file, "工单附件", "admin");

            // 将附件关联到工单
            workOrder.getAttachments().add(attachment);

            // 更新工单的最后修改时间
            workOrder.setLastModifiedTimestamp(LocalDateTime.now());

            // 保存更新后的工单信息
            workOrderRepository.save(workOrder);

            return ResponseEntity.ok("附件已上传");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Attachment saveAttachment(MultipartFile file, String description, String uploadedBy) {
        try {
            // 保存附件到存储系统，例如文件系统或云存储，并获取存储的URL
            String storageUrl = saveFileToStorage(file);

            // 创建附件对象并保存到数据库
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            attachment.setDescription(description);
            attachment.setUploadedBy(uploadedBy);
            attachment.setUrl(storageUrl);

            // 设置其他属性，如状态、版本等
            attachment.setStatus("草稿");
            attachment.setVersion(1);

            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            // 处理异常，例如文件存储失败
            e.printStackTrace();
            throw new RuntimeException("附件保存失败");
        }
    }

    private String saveFileToStorage(MultipartFile file) {
        // 实现附件保存到存储系统的逻辑，返回附件的存储URL或路径
        // 例如，可以将附件保存到文件系统或云存储，然后返回访问URL
        // 请根据实际存储系统进行实现
        // ...

        return "https://example.com/storage/" + file.getOriginalFilename();
    }

    @GetMapping("/{id}/attachments")
    public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long id) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();

            // 获取工单关联的附件列表
            List<Attachment> attachments = workOrder.getAttachments();

            return ResponseEntity.ok(attachments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/attachments/{attachmentId}")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long id, @PathVariable Long attachmentId) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(id);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();

            // 查找附件
            Optional<Attachment> optionalAttachment = workOrder.getAttachments().stream()
                    .filter(attachment -> attachment.getId().equals(attachmentId))
                    .findFirst();

            if (optionalAttachment.isPresent()) {
                Attachment attachment = optionalAttachment.get();
                // 实现附件下载逻辑，将附件内容返回给客户端
                // ...

                return ResponseEntity.ok("附件已下载");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    // 其他操作如更新优先级、查看历史记录等可以类似地实现
}
