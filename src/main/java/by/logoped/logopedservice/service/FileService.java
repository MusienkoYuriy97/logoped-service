package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.response.UploadResponse;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private String TEMP_URL = "";
    private String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/logoped-service/o/%s?alt=media";
    public UploadResponse upload(MultipartFile multipartFile) {
        log.info("Upload file");
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            TEMP_URL = this.uploadFile(file, fileName);
            file.delete();
            return UploadResponse
                    .builder()
                        .fileName(fileName)
                        .downloadLink(TEMP_URL)
                    .build();
        } catch (Exception e) {
            log.error("System error to load file");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("logoped-service", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./firebase-config.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
