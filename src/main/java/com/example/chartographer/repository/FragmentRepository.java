package com.example.chartographer.repository;

import com.example.chartographer.entity.Fragment;

public interface FragmentRepository {
    Fragment findById(String id);

    Fragment save(Fragment fragment);

    void delete(Fragment fragment);
}
