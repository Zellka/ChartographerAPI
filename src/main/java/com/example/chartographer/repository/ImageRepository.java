package com.example.chartographer.repository;

import com.example.chartographer.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, String> {
}
