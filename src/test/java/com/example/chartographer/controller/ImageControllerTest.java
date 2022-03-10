package com.example.chartographer.controller;

import com.example.chartographer.ChartographerApplicationTests;
import com.example.chartographer.model.ImageId;
import com.example.chartographer.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SpringBootTest
class ImageControllerTest {

    @Autowired
    private ImageController imageController;

    @Autowired
    public ImageControllerTest(ResourceLoader resourceLoader, FileRepository fileRepository) throws IOException {
        ReflectionTestUtils.setField(fileRepository, "path", ChartographerApplicationTests.getPathWithImages(resourceLoader));
    }

    @Test
    void createImage() {
        Assertions.assertEquals(HttpStatus.CREATED, imageController.createImage(1000, 1000).getStatusCode());
    }

    @Test
    void saveFragmentImage() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        ImageId imageId = (ImageId) imageController.createImage(100, 100).getBody();
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "bmp", stream);
            Assertions.assertEquals(HttpStatus.OK, imageController.saveFragmentImage(Integer.parseInt(imageId.getId()), 5, 5, 10, 10, stream.toByteArray()).getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getImage() {
        ImageId imageId = (ImageId) imageController.createImage(100, 100).getBody();
        Assertions.assertEquals(HttpStatus.OK, imageController.getImage(Integer.parseInt(imageId.getId()), 5, 5, 10, 10).getStatusCode());
        Assertions.assertNotNull(imageController.getImage(Integer.parseInt(imageId.getId()), 5, 5, 10, 10).getBody());
    }

    @Test
    void deleteImage() {
        ImageId imageId = (ImageId) imageController.createImage(100, 100).getBody();
        Assertions.assertEquals(HttpStatus.OK, imageController.deleteImage(Integer.parseInt(imageId.getId())).getStatusCode());
    }
}