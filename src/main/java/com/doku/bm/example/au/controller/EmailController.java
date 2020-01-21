package com.doku.bm.example.au.controller;

import com.doku.bm.example.au.dto.EmailDownloadRequest;
import com.doku.bm.example.au.service.EmailService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "Download from Email")
    @GetMapping(value = "/download")
    public ResponseEntity getEmail(@Validated @ModelAttribute EmailDownloadRequest emailDownloadRequest) throws IOException {
        emailService.get(emailDownloadRequest);
        return ResponseEntity.ok("DOWNLOAD FROM EMAIL SUCCESS");
    }
}
