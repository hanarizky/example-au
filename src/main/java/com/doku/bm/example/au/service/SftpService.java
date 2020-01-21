package com.doku.bm.example.au.service;

import com.doku.bm.example.au.dto.SftpDownloadRequest;
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
public class SftpService {

    @Autowired
    FileParserReadyService fileParserReadyService;

    @Value("${download.sftp.url}")
    private String sftpPath;

    @Value("${env.url}")
    private String envHost;

    public void get(SftpDownloadRequest sftpDownloadRequest) throws IOException {
        String url = envHost + sftpPath;
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("requestId", sftpDownloadRequest.getRequestId())
                .queryParam("clientId", sftpDownloadRequest.getClientId())
                .queryParam("message", sftpDownloadRequest.getMessage())
                .queryParam("username" , sftpDownloadRequest.getUsername())
                .queryParam("password" , sftpDownloadRequest.getPassword())
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

        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(
                uriSftp,
                Resource.class);
        InputStream result = responseEntity.getBody().getInputStream();
        String newFileName = responseEntity.getBody().getFilename();

        if (newFileName.contains(".zip")){
            fileParserReadyService.unzipFile(result, sftpDownloadRequest.getLocalPath());
        } else {
            fileParserReadyService.move2Local(result, sftpDownloadRequest.getLocalPath(), newFileName);
        }
    }

}
