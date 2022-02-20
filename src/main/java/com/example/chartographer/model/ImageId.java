package com.example.chartographer.model;

import com.example.chartographer.entity.Image;

public class ImageId {
    private String id;

    public static ImageId toModel(Image entity) {
        ImageId model = new ImageId();
        model.setId(entity.getId());
        return model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageId() {
    }
}
