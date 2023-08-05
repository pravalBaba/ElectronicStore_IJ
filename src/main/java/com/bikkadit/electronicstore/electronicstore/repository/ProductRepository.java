package com.bikkadit.electronicstore.electronicstore.repository;

import com.bikkadit.electronicstore.electronicstore.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findByTitleContaining(String subTitle);

    List<Product> findByLiveTrue();

}
