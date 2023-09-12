package com.edward.service.desk.pro.controller;

import com.edward.service.desk.pro.pojo.User;
import com.edward.service.desk.pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // 注册新用户
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // 检查用户名是否已存在
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("用户名已存在");
            }

            // 在实际应用中，可以加密密码、验证邮箱、生成激活链接等

            // 设置用户为启用状态
            user.setActive(true);

            // 保存用户到数据库
            User savedUser = userRepository.save(user);

            // 返回成功响应
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册用户时出错：" + e.getMessage());
        }
    }

    // 更新用户信息
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // 更新用户信息
                user.setUsername(updatedUser.getUsername());
                user.setPassword(updatedUser.getPassword());
                user.setEmail(updatedUser.getEmail());
                user.setRole(updatedUser.getRole());
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                user.setActive(updatedUser.isActive());
                // 可以根据需要更新其他属性

                // 保存更新后的用户信息
                User savedUser = userRepository.save(user);

                // 返回成功响应
                return ResponseEntity.ok(savedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新用户信息时出错：" + e.getMessage());
        }
    }

    // 删除用户
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("用户删除成功");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除用户时出错：" + e.getMessage());
        }
    }
}

