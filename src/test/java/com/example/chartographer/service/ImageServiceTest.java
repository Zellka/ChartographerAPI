package com.example.chartographer.service;

import com.example.chartographer.ChartographerApplicationTests;
import com.example.chartographer.controller.ImageController;
import com.example.chartographer.entity.Image;
import com.example.chartographer.exception.ImageBadRequestException;
import com.example.chartographer.exception.ImageNotFoundException;
import com.example.chartographer.exception.ImageNotOverlapException;
import com.example.chartographer.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    public ImageServiceTest(ResourceLoader resourceLoader, FileRepository fileRepository) throws IOException {
        ReflectionTestUtils.setField(fileRepository, "path", ChartographerApplicationTests.getPathWithImages(resourceLoader));
    }

    @Test
    void createImage() throws ImageBadRequestException, IOException {
        Assertions.assertEquals("0", imageService.createImage(100, 100).getId());
    }

    @Test
    void getImage() throws ImageNotOverlapException, ImageBadRequestException, IOException, ImageNotFoundException {
        Integer id = Integer.parseInt(imageService.createImage(100, 100).getId());
        Assertions.assertNotNull(imageService.getImage(id, 0, 0, 10, 10));
    }

    @Test
    void deleteImage() throws ImageBadRequestException, IOException, ImageNotFoundException {
        Integer id = Integer.parseInt(imageService.createImage(100, 100).getId());
        imageService.deleteImage(id);
    }
}