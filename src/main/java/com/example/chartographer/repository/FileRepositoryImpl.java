package com.example.chartographer.repository;

import com.example.chartographer.ChartographerApplication;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private final String path = ChartographerApplication.path;

    private static volatile Integer id = 0;

    private static final String IMAGE_FORMAT = "bmp";

    @Override
    public BufferedImage getFile(String nameFile) throws IOException {
        return ImageIO.read(new File(String.format("%s/%s", path, nameFile)));
    }

    @Override
    public String saveFile(BufferedImage image) throws IOException {
        File file = new File(String.format("%s/charta%d.bmp", path, id++));
        if (file.exists())
            Files.delete(Path.of(file.getPath()));
        ImageIO.write(image, IMAGE_FORMAT, file);
        return file.getName();
    }

    @Override
    public void updateFile(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, IMAGE_FORMAT, new File(this.path + "/" + path));
    }

    @Override
    public void deleteFile(String nameFile) throws IOException {
        Files.delete(Path.of(String.format("%s/%s", path, nameFile)));
    }
}
