package com.yagato.HololiveAPI.utils;

import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurClientTest {

    @Autowired
    ImgurClient imgurClient;

    private static Resource fileResource;

    private static MockMultipartFile image;

    @BeforeAll
    public static void setup() throws Exception {
        fileResource = new ClassPathResource("test.jpg");
        image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @DisplayName("Upload Image")
    @Test
    @Order(1)
    public void updloadImageTest() throws Exception {
        String base64URL = Base64.getEncoder().encodeToString(image.getBytes());
        String link = imgurClient.uploadImage(base64URL);

        assertTrue(link.contains("i.imgur.com"));
    }

    @DisplayName("Upload Image wrong Image Format")
    @Test
    @Order(2)
    public void UploadImageWrongImageFormatTest() throws Exception {
        String wrong = "";

        assertThrows(JSONException.class, () -> {imgurClient.uploadImage(wrong);});
    }

    @DisplayName("Upload Image Exceed Upload Limit")
    @Test
    @Order(3)
    public void UploadImageExceedUploadLimitTest() throws Exception {
        String base64URL = Base64.getEncoder().encodeToString(image.getBytes());

        assertThrows(RuntimeException.class, () -> {
            for(int i = 1; i <= 50; i++) {
                imgurClient.uploadImage(base64URL);
            }
        });
    }

}
