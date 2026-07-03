package com.happytohelp.homeloan.repo;



import com.happytohelp.homeloan.model.ContactMessage;
import com.happytohelp.homeloan.model.ContactStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByStatusOrderByIdDesc(ContactStatus status);
    List<ContactMessage> findAllByOrderByIdDesc();
}