package com.edward.service.desk.pro.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename; // 附件文件名
    private String contentType; // 附件内容类型
    private long size; // 附件大小
    private String description; // 附件描述
    private String status; // 附件状态 (例如：草稿、已发布、已归档)
    private int version; // 附件版本号
    private LocalDateTime uploadTimestamp; // 附件上传时间
    private String uploadedBy; // 上传人
    private String url; // 附件存储URL或路径

    // 其他属性、Getter/Setter 省略

    public Attachment() {
        // 默认构造函数
    }

    public Attachment(String filename, String contentType, long size, String description,
                      String status, int version, String uploadedBy, String url) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.description = description;
        this.status = status;
        this.version = version;
        this.uploadedBy = uploadedBy;
        this.url = url;
        this.uploadTimestamp = LocalDateTime.now();
    }

    // 其他构造函数、Getter/Setter 等方法
}

