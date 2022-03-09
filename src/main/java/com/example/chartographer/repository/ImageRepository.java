package com.example.chartographer.repository;

import com.example.chartographer.entity.Image;

public interface ImageRepository {

    Image getImage(Integer id);

    Image saveImage(Image image);

    void deleteImage(Integer id);
}
