package com.example.chartographer.repository;

import com.example.chartographer.entity.Image;

public interface ImageRepository {
    Image findById(String id);

    Image save(Image image);

    void delete(Image image);
}
