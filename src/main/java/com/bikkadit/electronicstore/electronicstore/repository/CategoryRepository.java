package com.bikkadit.electronicstore.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.electronicstore.electronicstore.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String>
{
}
