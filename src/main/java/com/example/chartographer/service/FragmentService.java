package com.example.chartographer.service;

import com.example.chartographer.entity.Fragment;
import com.example.chartographer.exception.ImageNotOverlapException;
import com.example.chartographer.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FragmentService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileService fileService;

    private static final int SIZE = 5000;

    public List<Fragment> getFragments(Integer width, Integer height) {
        List<Fragment> fragments = new ArrayList<>();
        for (int y = 0; y < height; y += Math.min(SIZE, height - y)) {
            for (int x = 0; x < width; x += Math.min(SIZE, width - x))
                fragments.add(new Fragment(Math.min(SIZE, width - x), Math.min(SIZE, height - y), x, y));
        }
        return fragments;
    }

    public void createFragments(List<Fragment> fragments) throws IOException {
        for (Fragment fragment : fragments) {
            fragment.setPath(fileService.createBasicImage(fragment.getWidth(), fragment.getHeight()));
        }
    }

    public List<Fragment> getIntersectingFragments(List<Fragment> fragments, int x, int y, int width, int height) throws ImageNotOverlapException {
        List<Fragment> filteredFragments = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if ((fragment.getX() < (x + width)) && (fragment.getY() < (y + height)) && (x < (fragment.getX() + fragment.getWidth())) && (y < (fragment.getY() + fragment.getHeight())))
                filteredFragments.add(fragment);
        }
        if (filteredFragments.isEmpty())
            throw new ImageNotOverlapException("Фрагмент не пересекается с изображением");
        return filteredFragments;
    }


    public void deleteFragments(List<Fragment> fragments) throws IOException {
        for (Fragment fragment : fragments)
            fileRepository.deleteFile(fragment.getPath());
        fragments.clear();
    }
}
