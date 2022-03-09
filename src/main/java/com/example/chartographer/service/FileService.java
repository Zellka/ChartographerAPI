package com.example.chartographer.service;

import com.example.chartographer.entity.Fragment;
import com.example.chartographer.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private BufferedImage getBasicImage(Integer width, Integer height) {
        BufferedImage basicImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphicImage = basicImage.createGraphics();
        graphicImage.fillRect(0, 0, width, height);
        graphicImage.setColor(Color.BLACK);
        return basicImage;
    }

    public String createBasicImage(Integer width, Integer height) throws IOException {
        return fileRepository.saveFile(getBasicImage(width, height));
    }

    public void overlayImage(BufferedImage image, List<Fragment> fragments, Integer x, Integer y, Integer width, Integer height) throws IOException {
        for (Fragment fragment : fragments) {
            int x0 = Math.max(x, fragment.getX());
            int x1 = Math.min(x + width, fragment.getX() + fragment.getWidth());
            int y0 = Math.max(y, fragment.getY());
            int y1 = Math.min(y + height, fragment.getY() + fragment.getHeight());

            BufferedImage partImage = fileRepository.getFile(fragment.getPath());
            partImage.createGraphics().drawImage(image, x0 - fragment.getX(), y0 - fragment.getY(), x1 - x0, y1 - y0, null);
            fileRepository.updateFile(partImage, fragment.getPath());
        }
    }

    public BufferedImage overlayFragments(List<Fragment> fragments, Integer x, Integer y, Integer width, Integer height) throws IOException {
        BufferedImage image = getBasicImage(width, height);
        for (Fragment fragment : fragments) {
            int x0 = Math.max(x, fragment.getX());
            int x1 = Math.min(x + width, fragment.getX() + fragment.getWidth());
            int y0 = Math.max(y, fragment.getY());
            int y1 = Math.min(y + height, fragment.getY() + fragment.getHeight());

            BufferedImage partImage = fileRepository.getFile(fragment.getPath()).getSubimage(x0 - fragment.getX(), y0 - fragment.getY(), x1 - x0, y1 - y0);
            image.createGraphics().drawImage(partImage, x0 - x, y0 - y, x1 - x0, y1 - y0, null);
        }
        return image;
    }
}
