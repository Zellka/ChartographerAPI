package com.example.chartographer.entity;

public class Fragment {

    private String path;
    private Integer width;
    private Integer height;
    private Integer x;
    private Integer y;

    public Fragment(Integer width, Integer height, Integer x, Integer y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
