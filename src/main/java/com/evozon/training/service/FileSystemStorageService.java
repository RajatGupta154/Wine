package com.evozon.training.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class FileSystemStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);
    @Value("${file.upload.directory}")
    private String UPLOAD_DIRECTORY;

    public String store(MultipartFile file) throws IOException {
        String generatedID = UUID.randomUUID().toString();
        String fileName = generatedID + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        final Path UPLOAD_ABSOLUTE_PATH = Paths.get(UPLOAD_DIRECTORY);
        try {
            if (file.isEmpty()) {
                logger.warn("Failed to store empty file " + fileName);
                throw new IOException("Failed to store empty file ");
            }
            InputStream inputStream = file.getInputStream();
            Path resolve = UPLOAD_ABSOLUTE_PATH.resolve(fileName);
            Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            logger.warn("Failed to store file " + fileName, e);
            throw new IOException(e);
        }
        String fullPath = UPLOAD_ABSOLUTE_PATH.resolve(fileName).toUri().toURL().toString().substring(6);
        return fullPath;
    }

    public String storeDefault() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("../../resources/images/default-image.jpg").getFile());
        String generatedID = UUID.randomUUID().toString();
        String fileName = generatedID + ".jpg";
        final Path UPLOAD_ABSOLUTE_PATH = Paths.get(UPLOAD_DIRECTORY);
        try {
            InputStream inputStream = new FileInputStream(file);
            Path resolve = UPLOAD_ABSOLUTE_PATH.resolve(fileName);
            Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            logger.warn("Failed to store file " + fileName, e);
            throw new IOException(e);
        }
        String fullPath = UPLOAD_ABSOLUTE_PATH.resolve(fileName).toUri().toURL().toString().substring(6);
        return fullPath;
    }
}
