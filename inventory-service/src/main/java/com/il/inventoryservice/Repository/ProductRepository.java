package com.il.inventoryservice.Repository;

import com.il.inventoryservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
