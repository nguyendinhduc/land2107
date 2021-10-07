package com.t3h.buoi12.repository;

import com.t3h.buoi12.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(nativeQuery = true, value = "select * from category where is_delete = false")
    List<Category> findAll();
}
