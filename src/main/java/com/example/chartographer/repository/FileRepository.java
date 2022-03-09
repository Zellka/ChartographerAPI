package com.example.chartographer.repository;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FileRepository {

    BufferedImage getFile(String nameFile) throws IOException;

    String saveFile(BufferedImage image) throws IOException;

    void updateFile(BufferedImage image, String path) throws IOException;

    void deleteFile(String nameFile) throws IOException;
}
