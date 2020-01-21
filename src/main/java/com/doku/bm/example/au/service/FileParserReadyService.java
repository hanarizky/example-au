package com.doku.bm.example.au.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class FileParserReadyService {

    public static void unzipFile(InputStream fileZip, String dest) throws IOException {
        File destDir = new File(dest);

        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(fileZip);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            log.info("Unzip " + newFile.getName() + " Success");
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public void move2Local(InputStream file, String dest, String newFile) throws IOException {
        File targetFile = new File(dest + "/" + newFile);
        FileUtils.copyInputStreamToFile(file, targetFile);
        log.info("Move " + newFile + " Success");
    }
}
