package com.fdf.liga_mx.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaStorageServiceTest {

    @Mock
    private S3Client s3;

    @InjectMocks
    private MediaStorageService mediaStorageService;

    private static final String BUCKET_NAME = "test-bucket";
    private static final long MAX_SIZE = 1048576L; // 1MB

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mediaStorageService, "bucket", BUCKET_NAME);
        ReflectionTestUtils.setField(mediaStorageService, "maxSizeBytes", MAX_SIZE);
    }

    @Test
    void buildStorageKey_shouldReturnFormattedKey() {
        String uuid = UUID.randomUUID().toString();
        String ext = "JPG";
        String expected = uuid + ".jpg";

        String result = mediaStorageService.buildStorageKey(uuid, ext);

        assertEquals(expected, result);
    }

    @Test
    void uploadFile_shouldUploadToS3AndReturnKey() throws IOException {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                "test data".getBytes()
        );

        // Act
        String result = mediaStorageService.uploadFile(file, uuid);

        // Assert
        assertEquals(uuid + ".png", result);
        verify(s3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void replaceFile_shouldDeleteOldAndUploadNew() throws IOException {
        // Arrange
        String oldKey = "old-uuid.jpg";
        String newUuid = UUID.randomUUID().toString();
        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "new.png",
                "image/png",
                "new data".getBytes()
        );

        // Act
        String result = mediaStorageService.replaceFile(oldKey, newUuid, newFile);

        // Assert
        assertEquals(newUuid + ".png", result);
        verify(s3).deleteObject(any(DeleteObjectRequest.class));
        verify(s3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void getFileBytes_shouldReturnByteArray() {
        // Arrange
        String key = "test.jpg";
        byte[] expectedBytes = "content".getBytes();
        ResponseBytes<GetObjectResponse> responseBytes = mock(ResponseBytes.class);
        when(responseBytes.asByteArray()).thenReturn(expectedBytes);
        when(s3.getObject(any(GetObjectRequest.class), any(ResponseTransformer.class))).thenReturn(responseBytes);

        // Act
        byte[] result = mediaStorageService.getFileBytes(key);

        // Assert
        assertArrayEquals(expectedBytes, result);
    }

    @Test
    void delete_shouldCallS3Delete() {
        String key = "delete.jpg";

        mediaStorageService.delete(key);

        verify(s3).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void deleteAll_shouldCallS3DeleteForEachKey() {
        List<String> keys = List.of("key1.jpg", "key2.png");

        mediaStorageService.deleteAll(keys);

        verify(s3, times(2)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void verifySizeFiles_shouldReturnTrue_whenAllFilesAreUnderLimit() {
        // Arrange
        List<String> keys = List.of("small.jpg");
        HeadObjectResponse headResponse = HeadObjectResponse.builder()
                .contentLength(100L)
                .build();
        when(s3.headObject(any(HeadObjectRequest.class))).thenReturn(headResponse);

        // Act
        boolean result = mediaStorageService.verifySizeFiles(keys);

        // Assert
        assertTrue(result);
    }

    @Test
    void verifySizeFiles_shouldReturnFalse_whenAFileExceedsLimit() {
        // Arrange
        List<String> keys = List.of("big.jpg");
        HeadObjectResponse headResponse = HeadObjectResponse.builder()
                .contentLength(MAX_SIZE + 1)
                .build();
        when(s3.headObject(any(HeadObjectRequest.class))).thenReturn(headResponse);

        // Act
        boolean result = mediaStorageService.verifySizeFiles(keys);

        // Assert
        assertFalse(result);
    }
}
