package com.example.chartographer.repository;

import com.example.chartographer.entity.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageRepositoryImplTest {

    @Autowired
    private ImageRepositoryImpl imageRepository;

    @Test
    void getImage() {
        Image image = new Image();
        image.setWidth(10);
        image.setHeight(10);
        imageRepository.saveImage(image);
        Assertions.assertEquals(image, imageRepository.getImage(image.getId()));
    }

    @Test
    void saveImage() {
        Image image = new Image();
        image.setWidth(10);
        image.setHeight(10);
        Assertions.assertEquals(image, imageRepository.saveImage(image));
    }

    @Test
    void deleteImage() {
        Image image = new Image();
        image.setWidth(10);
        image.setHeight(10);
        imageRepository.saveImage(image);
        imageRepository.deleteImage(image.getId());
        Assertions.assertNull(imageRepository.getImage(image.getId()));
    }
}