package com.example.chartographer.repository;

import com.example.chartographer.ChartographerApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

@SpringBootTest
class FileRepositoryImplTest {

    private final FileRepositoryImpl fileRepository;

    @Autowired
    public FileRepositoryImplTest(FileRepositoryImpl fileRepository, ResourceLoader resourceLoader) throws IOException {
        this.fileRepository = fileRepository;
        String classPath = ChartographerApplicationTests.getPathWithImages(resourceLoader);
        ReflectionTestUtils.setField(fileRepository, "path", classPath);
    }

    @Test
    void getFile() throws IOException {
        Assertions.assertNotNull(fileRepository.getFile("charta0.bmp"));
    }

    @Test
    void saveFile() throws IOException {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        Assertions.assertNotNull(fileRepository.getFile(fileRepository.saveFile(image)));
    }

    @Test
    void deleteFile() throws IOException {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        fileRepository.deleteFile(fileRepository.saveFile(image));
    }
}