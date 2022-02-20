package com.example.chartographer.controller;

import com.example.chartographer.exception.ImageBadRequestException;
import com.example.chartographer.exception.ImageNotFoundException;
import com.example.chartographer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chartas")
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping("/")
    public ResponseEntity createImage(@RequestParam Long width, @RequestParam Long height) {
        try {
            return ResponseEntity.ok(service.createImage(width, height));
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @PostMapping("/{id}/")
    public ResponseEntity saveFragmentImage(@PathVariable String id, @RequestParam Integer x, @RequestParam Integer y, @RequestParam Long width, @RequestParam Long height) {
        try {
            service.saveFragmentImage(id, x, y, width, height);
            return ResponseEntity.ok("Восстановленный фрагмент сохранён");
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity getImage(@PathVariable String id, @RequestParam Integer x, @RequestParam Integer y, @RequestParam Long width, @RequestParam Long height) {
        try {
            return ResponseEntity.ok(service.getImage(id, x, y, width, height));
        } catch (ImageBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteImage(@PathVariable String id) {
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
