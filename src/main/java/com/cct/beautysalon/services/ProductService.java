package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.ProductDTO;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Product;
import com.cct.beautysalon.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    /**
     * convert to DTO
     * @param product
     * @return
     */
    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setItemType("Product");
        mapper.map(product, dto);
        return dto;
    }

    /**
     * convert to entity
     * @param productDTO
     * @return
     */
    private Product toEntity(ProductDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }

    /**
     * find all products
     * @return
     */
    public List<ProductDTO> findAll() {

        Sort sort = Sort.by("name").ascending();

        var products = StreamSupport.stream(productRepository.findAll(sort).spliterator(), false)
                .collect(Collectors.toList());
        return products.stream().map(this::toDTO).toList();
    }

    /**
     * find product by id
     * @param id
     * @return
     */
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                        () -> new NotFoundException("Product by id " + id + " not found"));
        return toDTO(product);
    }

    /**
     * saves the product
     * @param productDTO
     * @return
     */
    public ProductDTO save(ProductDTO productDTO) {
        Product product = toEntity(productDTO);
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    /**
     * update product
     * @param id
     * @param productDTO
     */
    public void update(Long id, ProductDTO productDTO) {
        findOrThrowProductById(id);
        Product product = toEntity(productDTO);
        productRepository.save(product);
    }

    /**
     * find product or throw exception
     * @param id
     * @return
     */
    private Product findOrThrowProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Product by id: " + id + "was not found"));
    }

    /**
     * delete a product
     * @param id
     */
    public void delete(Long id) {
        findOrThrowProductById(id);
        productRepository.deleteById(id);
    }
}
