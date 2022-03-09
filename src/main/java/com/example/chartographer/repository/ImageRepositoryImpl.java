package com.example.chartographer.repository;

import com.example.chartographer.entity.Image;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private HashMap<Integer, Image> images = new HashMap<>();

    private static volatile Integer id = 0;

    @Override
    public Image getImage(Integer id) {
        return images.get(id);
    }

    @Override
    public Image saveImage(Image image) {
        image.setId(id++);
        images.put(image.getId(), image);
        return image;
    }

    @Override
    public void deleteImage(Integer id) {
        images.remove(id);
    }
}
