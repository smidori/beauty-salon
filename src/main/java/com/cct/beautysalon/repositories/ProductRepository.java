package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>  {

}
