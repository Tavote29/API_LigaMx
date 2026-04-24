package com.fdf.liga_mx.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaStorageService {

    private final S3Client s3;

    @Value("${app.s3.bucket}")
    private String bucket;

    @Value("${app.s3.maxSizeBytes}")
    private long maxSizeBytes;



    public String buildStorageKey(String uuid, String ext) {
        return "%s.%s".formatted(uuid, ext.toLowerCase());
    }

    public String uploadFile(MultipartFile file,String uuid) throws IOException {

        String ext = file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")
                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                : "";

        String storageKey = buildStorageKey(uuid, ext);


        try (InputStream inputStream = file.getInputStream()) {
            backendUpload(storageKey, inputStream, file.getSize(), file.getContentType());
        }

        return storageKey;
    }

    public String replaceFile(String oldStorageKey, String uuid, MultipartFile newFile) throws IOException {

        if (oldStorageKey != null && !oldStorageKey.isBlank()) {
            delete(oldStorageKey);
        }
        return uploadFile(newFile,uuid);
    }

    public byte[] getFileBytes(String storageKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build();

        return s3.getObject(getObjectRequest, ResponseTransformer.toBytes()).asByteArray();
    }



    public HeadObjectResponse headObject(String storageKey) {
        return s3.headObject(HeadObjectRequest.builder().bucket(bucket).key(storageKey).build());
    }

    public void backendUpload(String storageKey, InputStream inputStream, long contentLength, String contentType) {
        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(storageKey)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromInputStream(inputStream, contentLength)
        );
    }

    public void delete(String storageKey) {
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(storageKey).build());
    }

    public void deleteAll(List<String> storageKeys) {

        storageKeys.forEach(item -> {
            s3.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(item).build());
        });

    }


    public InputStream getFileStream(String storageKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build();


        return s3.getObject(getObjectRequest);
    }

    public boolean verifySizeFiles(List<String> confirmMediaStorageKeyId) {

        for (String key : confirmMediaStorageKeyId) {

            var head = headObject(key);

            if (head.contentLength() > maxSizeBytes) {

                return false;

            }

        }

        return true;
    }

}
