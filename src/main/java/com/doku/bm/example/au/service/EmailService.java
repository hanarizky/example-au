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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

        String uri = envHost + emailPath;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
                // Add query parameter
                .queryParam("requestId", emailDownloadRequest.getRequestId())
                .queryParam("clientId", emailDownloadRequest.getClientId())
                .queryParam("message", emailDownloadRequest.getMessage())
                .queryParam("host", emailDownloadRequest.getHost())
                .queryParam("port", emailDownloadRequest.getPort())
                .queryParam("username" , URLEncoder.encode(emailDownloadRequest.getUsername(), StandardCharsets.UTF_8.toString()))
                .queryParam("password" , URLEncoder.encode(emailDownloadRequest.getPassword(), StandardCharsets.UTF_8.toString()))
                .queryParam("labelName", emailDownloadRequest.getLabelName());

        String uriEmail = builder.build().toString();
        log.info("URL :: " + uriEmail);

        URL url = new URL(uriEmail);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        InputStream result = con.getInputStream();

        fileParserReadyService.unzipFile(result, emailDownloadRequest.getLocalPath());
    }
}
