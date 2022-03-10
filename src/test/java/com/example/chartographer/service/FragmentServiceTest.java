package com.example.chartographer.service;

import com.example.chartographer.ChartographerApplicationTests;
import com.example.chartographer.entity.Fragment;
import com.example.chartographer.exception.ImageNotOverlapException;
import com.example.chartographer.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FragmentServiceTest {

    @Autowired
    private FragmentService fragmentService;

    @Autowired
    public FragmentServiceTest (ResourceLoader resourceLoader, FileRepository fileRepository) throws IOException {
        ReflectionTestUtils.setField(fileRepository, "path", ChartographerApplicationTests.getPathWithImages(resourceLoader));
    }

    @Test
    void getFragments() {
        Assertions.assertNotNull(fragmentService.getFragments(60, 50));
    }

    @Test
    void getIntersectingFragments() throws ImageNotOverlapException {
        List<Fragment> filterList = new ArrayList<>();
        filterList.add(new Fragment(100, 100, 50, 40));
        filterList.add(new Fragment(5000, 5000, 4000, 3000));

        List<Fragment> actualList = fragmentService.getIntersectingFragments(filterList, 50, 40, 100, 100);

        List<Fragment> expectedList = new ArrayList<>();
        expectedList.add(new Fragment(100, 100, 50, 40));

        Assertions.assertEquals(expectedList.get(0).getWidth(), actualList.get(0).getWidth());
    }

    @Test
    void deleteFragments() throws IOException {
        List<Fragment> fragments = fragmentService.getFragments(100, 100);
        fragmentService.createFragments(fragments);
        fragmentService.deleteFragments(fragments);
        Assertions.assertTrue(fragments.isEmpty());
    }
}