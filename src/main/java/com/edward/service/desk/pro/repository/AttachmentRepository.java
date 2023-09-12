package com.edward.service.desk.pro.repository;

import com.edward.service.desk.pro.pojo.Attachment;
import com.edward.service.desk.pro.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}

