package com.example.chartographer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class ChartographerApplication {
    public static String path;

    public static void main(String[] args) throws IOException {
        //path = args[0];
        path = "images";
        Path imagePath = Path.of(path);

        if (!Files.exists(imagePath)) {
            Files.createDirectories(imagePath);
        }
        SpringApplication.run(ChartographerApplication.class, args);
    }
}
