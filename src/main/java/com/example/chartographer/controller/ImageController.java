package com.example.chartographer.controller;

import com.example.chartographer.exception.ImageBadRequestException;
import com.example.chartographer.exception.ImageNotFoundException;
import com.example.chartographer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/chartas")
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping("/")
    public ResponseEntity createImage(@RequestParam Integer width, @RequestParam Integer height) {
        try {
            return new ResponseEntity<>(service.createImage(width, height), HttpStatus.CREATED);
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @PostMapping(value = "/{id}/", consumes = "image/bmp")
    public ResponseEntity saveFragmentImage(@PathVariable Integer id, @RequestParam Integer x, @RequestParam Integer y, @RequestParam Integer width, @RequestParam Integer height, @RequestBody byte[] image) {
        try {
            service.saveFragmentImage(id, image, x, y, width, height);
            return ResponseEntity.ok("Восстановленный фрагмент сохранён");
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping(value = "/{id}/", produces = "image/bmp")
    public ResponseEntity getImage(@PathVariable Integer id, @RequestParam Integer x, @RequestParam Integer y, @RequestParam Integer width, @RequestParam Integer height) {
        try {
            byte[] image = service.getImage(id, x, y, width, height);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImage(@PathVariable Integer id) {
        try {
            service.deleteImage(id);
            return ResponseEntity.ok("Изображение удалено");
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }
}
