package com.edward.service.desk.pro.repository;

import com.edward.service.desk.pro.pojo.User;
import com.edward.service.desk.pro.pojo.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

