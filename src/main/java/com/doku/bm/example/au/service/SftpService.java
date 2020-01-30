package com.doku.bm.example.au.service;

import com.doku.bm.example.au.dto.SftpDownloadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class SftpService {

    @Autowired
    FileParserReadyService fileParserReadyService;

    @Value("${download.sftp.url}")
    private String sftpPath;

    @Value("${env.url}")
    private String envHost;

    public void get(SftpDownloadRequest sftpDownloadRequest) throws IOException {
        String uri = envHost + sftpPath;

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
                // Add query parameter
                .queryParam("requestId", sftpDownloadRequest.getRequestId())
                .queryParam("clientId", sftpDownloadRequest.getClientId())
                .queryParam("message", sftpDownloadRequest.getMessage())
                .queryParam("username" , URLEncoder.encode(sftpDownloadRequest.getUsername(), StandardCharsets.UTF_8.toString()))
                .queryParam("password" , URLEncoder.encode(sftpDownloadRequest.getPassword(), StandardCharsets.UTF_8.toString()))
                .queryParam("host", sftpDownloadRequest.getHost())
                .queryParam("port", sftpDownloadRequest.getPort())
                .queryParam("remotePath", sftpDownloadRequest.getRemotePath());

        String uriSftp = builder.build().toString();

        log.info("URL :: " + uriSftp);

/**
 *
 * This commented code for extract response without save it to the memory
 */

//        InputStream result = restTemplate.execute(
//                uriEmail,
//                HttpMethod.GET,
//                (ClientHttpRequest requestCallback) -> {},
//                responseExtractor -> {
//                    IOUtils.copy(responseExtractor.getBody(), response.getOutputStream());
//                    return null;
//                });

        URL url = new URL(uriSftp);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream result = con.getInputStream();


/**
 *
 * This commented code for hit URL using rest template, but consider the URL decode and encode
 */
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(
//                uriSftp,
//                Resource.class);
//        InputStream result = responseEntity.getBody().getInputStream();
//        String newFileName = responseEntity.getBody().getFilename();

        String newFileName = sftpDownloadRequest.getRemotePath().substring(sftpDownloadRequest.getRemotePath().lastIndexOf('/') + 1);

        if (!newFileName.contains(".")){
            fileParserReadyService.unzipFile(result, sftpDownloadRequest.getLocalPath());
        } else {
            fileParserReadyService.move2Local(result, sftpDownloadRequest.getLocalPath(), newFileName);
        }
    }

}
