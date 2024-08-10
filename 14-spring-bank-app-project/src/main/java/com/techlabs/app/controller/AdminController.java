package com.techlabs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.AccountDTO;
import com.techlabs.app.dto.ResponseMessageDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
    private AdminService adminService;

    @PostMapping("/create-account/{personId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable Long personId) {
        Account account = adminService.createAccountForPerson(personId);
        AccountDTO accountDTO = new AccountDTO(account.getId(), account.getBalance());
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity<ResponseMessageDTO> deleteAccount(@PathVariable Long accountId) {
        // Implement account deletion logic
        return new ResponseEntity<>(new ResponseMessageDTO("Account deleted successfully"), HttpStatus.OK);
    }
}
