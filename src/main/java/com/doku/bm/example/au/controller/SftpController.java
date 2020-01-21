package com.doku.bm.example.au.controller;

import com.doku.bm.example.au.dto.SftpDownloadRequest;
import com.doku.bm.example.au.service.SftpService;
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
@RequestMapping("/sftp")
public class SftpController {

    @Autowired
    private SftpService sftpService;

    @ApiOperation(value = "Download from SFTP")
    @GetMapping(value = "/download")
    public ResponseEntity getSftpFiles(@Validated @ModelAttribute SftpDownloadRequest sftpDownloadRequest) throws IOException {
        sftpService.get(sftpDownloadRequest);
        return ResponseEntity.ok("DOWNLOAD FROM SFTP SUCCESS");
    }

}
