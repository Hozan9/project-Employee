package com.example.backend.model;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class CloudinaryServiceTest {
    Cloudinary cloudinary = mock(Cloudinary.class);
    Uploader uploader = mock(Uploader.class);
    CloudinaryService cloudinaryService = new CloudinaryService(cloudinary);
    @Test
    void uploadImage() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test".getBytes());
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(Map.of("url", "test-url"));
        String actual = cloudinaryService.uploadImage(mockMultipartFile);
        verify(uploader).upload(any(), any());
        assertEquals("test-url", actual);
    }
}