package com.doku.bm.example.au.service;

import com.doku.bm.example.au.dto.EmailDownloadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class EmailService {

    @Autowired
    FileParserReadyService fileParserReadyService;

    @Value("${download.email.url}")
    private String emailPath;

    @Value("${env.url}")
    private String envHost;

    public void get(EmailDownloadRequest emailDownloadRequest) throws IOException {
        String url = envHost + emailPath;
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("requestId", emailDownloadRequest.getRequestId())
                .queryParam("clientId", emailDownloadRequest.getClientId())
                .queryParam("message", emailDownloadRequest.getMessage())
                .queryParam("host", emailDownloadRequest.getHost())
                .queryParam("port", emailDownloadRequest.getPort())
                .queryParam("username" , emailDownloadRequest.getUsername())
                .queryParam("password" , emailDownloadRequest.getPassword())
                .queryParam("labelName", emailDownloadRequest.getLabelName());

        String uriEmail = builder.build().toString();

        log.info("URL :: " + uriEmail);

        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(
                uriEmail,
                Resource.class);

        InputStream result = responseEntity.getBody().getInputStream();
        fileParserReadyService.unzipFile(result, emailDownloadRequest.getLocalPath());
    }
}
