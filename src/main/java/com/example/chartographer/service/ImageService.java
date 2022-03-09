package com.example.chartographer.service;

import com.example.chartographer.entity.Fragment;
import com.example.chartographer.entity.Image;
import com.example.chartographer.exception.ImageBadRequestException;
import com.example.chartographer.exception.ImageNotFoundException;
import com.example.chartographer.exception.ImageNotOverlapException;
import com.example.chartographer.model.ImageId;
import com.example.chartographer.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FragmentService fragmentService;

    private static final int MAX_WIDTH = 20000;

    private static final int MAX_HEIGHT = 50000;

    private static final String IMAGE_FORMAT = "bmp";

    public ImageId createImage(Integer width, Integer height) throws ImageBadRequestException, IOException {
        Image image = new Image();
        if ((width > 0 && width <= MAX_WIDTH) && (height > 0 && height <= MAX_HEIGHT)) {
            image.setWidth(width);
            image.setHeight(height);
            image.setFragments(fragmentService.getFragments(width, height));
            fragmentService.createFragments(image.getFragments());
        } else {
            throw new ImageBadRequestException("Данные некорректны");
        }
        return ImageId.toModel(imageRepository.saveImage(image));
    }

    public void saveFragmentImage(Integer id, byte[] image, Integer x, Integer y, Integer width, Integer height) throws ImageNotFoundException, ImageBadRequestException, ImageNotOverlapException {
        if (imageRepository.getImage(id) != null) {
            if ((width > 0 && width <= MAX_WIDTH) && (height > 0 && height <= MAX_HEIGHT)) {
                List<Fragment> fragments = fragmentService.getIntersectingFragments(imageRepository.getImage(id).getFragments(), x, y, width, height);
                BufferedImage convertImage;
                try (ByteArrayInputStream stream = new ByteArrayInputStream(image)) {
                    convertImage = ImageIO.read(stream);
                    fileService.overlayImage(convertImage, fragments, x, y, width, height);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new ImageBadRequestException("Данные некорректны");
            }
        } else {
            throw new ImageNotFoundException();
        }
    }

    public byte[] getImage(Integer id, Integer x, Integer y, Integer width, Integer height) throws ImageNotFoundException, ImageBadRequestException, ImageNotOverlapException, IOException {
        if (imageRepository.getImage(id) != null) {
            if ((width > 0 && width <= MAX_WIDTH) && (height > 0 && height <= MAX_HEIGHT)) {
                List<Fragment> fragments = fragmentService.getIntersectingFragments(imageRepository.getImage(id).getFragments(), x, y, width, height);
                BufferedImage image = fileService.overlayFragments(fragments, x, y, width, height);

                byte[] convertImage;
                try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                    ImageIO.write(image, IMAGE_FORMAT, stream);
                    convertImage = stream.toByteArray();
                    return convertImage;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new ImageBadRequestException("Данные некорректны");
            }
        } else {
            throw new ImageNotFoundException();
        }
        return null;
    }

    public void deleteImage(Integer id) throws ImageNotFoundException, IOException {
        Image image = imageRepository.getImage(id);
        if (image != null) {
            fragmentService.deleteFragments(image.getFragments());
            imageRepository.deleteImage(id);
        } else {
            throw new ImageNotFoundException();
        }
    }
}
