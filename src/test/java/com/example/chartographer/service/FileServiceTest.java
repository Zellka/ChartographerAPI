package com.example.chartographer.service;

import com.example.chartographer.ChartographerApplicationTests;
import com.example.chartographer.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    public FileServiceTest(ResourceLoader resourceLoader, FileRepository fileRepository) throws IOException {
        ReflectionTestUtils.setField(fileRepository, "path", ChartographerApplicationTests.getPathWithImages(resourceLoader));
    }

    @Test
    void createBasicImage() throws IOException {
        Assertions.assertEquals("charta0.bmp", fileService.createBasicImage(100, 100));
    }
}