package com.example.chartographer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@SpringBootTest
public class ChartographerApplicationTests {

    public static String getPathWithImages(ResourceLoader resourceLoader) throws IOException {
        String imagesPath = resourceLoader.getResource("classpath:images/charta0.bmp").getFile().getAbsolutePath().replace("charta0.bmp", "");
        return imagesPath;
    }

    @Test
    void contextLoads() {
    }
}
