package com.example.chartographer.service;

import com.example.chartographer.entity.Image;
import com.example.chartographer.exception.ImageBadRequestException;
import com.example.chartographer.exception.ImageNotFoundException;
import com.example.chartographer.model.ImageId;
import com.example.chartographer.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private ImageRepository repository;

    public ImageId createImage(Long width, Long height) throws ImageBadRequestException {
        Image image = new Image();
        if ((width > 0 && width <= 20000) && (height > 0 && height <= 50000)) {
            image.setWidth(width);
            image.setHeight(height);
        } else {
            throw new ImageBadRequestException("Данные некорректны");
        }
        return ImageId.toModel(repository.save(image));
    }

    public Image saveFragmentImage(String id, Integer x, Integer y, Long width, Long height) throws ImageNotFoundException, ImageBadRequestException {
        Image image;
        if (repository.findById(id) != null) {
            if ((width > 0 && width <= 20000) && (height > 0 && height <= 50000)) {
                image = repository.findById(id);
                image.setWidth(width);
                image.setHeight(height);
            } else {
                throw new ImageBadRequestException("Данные некорректны");
            }
        } else {
            throw new ImageNotFoundException();
        }
        return repository.save(image);
    }

    public Image getImage(String id, Integer x, Integer y, Long width, Long height) throws ImageNotFoundException, ImageBadRequestException {
        Image image;
        if (repository.findById(id) != null) {
            if ((width > 0 && width <= 20000) && (height > 0 && height <= 50000)) {
                image = repository.findById(id);
            } else {
                throw new ImageBadRequestException("Данные некорректны");
            }
        } else {
            throw new ImageNotFoundException();
        }
        return image;
    }

    public void deleteImage(String id) throws ImageNotFoundException {
        Image image;
        if (repository.findById(id) != null) {
            image = repository.findById(id);
            repository.delete(image);
        } else {
            throw new ImageNotFoundException();
        }
    }
}
