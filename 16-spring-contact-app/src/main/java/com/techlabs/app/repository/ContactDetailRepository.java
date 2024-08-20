package com.techlabs.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;

public interface ContactDetailRepository extends JpaRepository<ContactDetail, Integer> {

	Page<ContactDetail> findByContact(Contact contact, PageRequest pageRequest);

}
