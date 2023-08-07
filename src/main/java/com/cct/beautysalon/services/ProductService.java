package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Product;
import com.cct.beautysalon.repositories.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> findAll() {
        Sort sort = Sort.by("name").ascending();
        return productRepository.findAll(sort);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Product by id "+ id+" not found"));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
    public void update(Long id, Product product) {
        findOrThrowProductById(id);
        productRepository.save(product);
    }

    private Product findOrThrowProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Product by id: " + id + "was not found"));
    }

    public void delete(Long id) {
        findOrThrowProductById(id);
        productRepository.deleteById(id);
    }
}
