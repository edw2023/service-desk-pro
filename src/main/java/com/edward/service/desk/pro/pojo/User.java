package com.edward.service.desk.pro.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role; // 用户权限级别
    private String firstName;
    private String lastName;
    private boolean active; // 用户状态，例如：启用/禁用

    // 其他属性和Getter/Setter省略
}

