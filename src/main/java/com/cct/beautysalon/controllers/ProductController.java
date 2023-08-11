package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.ProductDTO;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Product;
import com.cct.beautysalon.services.ProductService;
import com.cct.beautysalon.utils.ErrorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * get all products
     *
     * @return
     */
    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.findAll();
    }

    /**
     * save product
     *
     * @param productDTO
     * @return
     */
    @PostMapping
    public ProductDTO save(@Valid @RequestBody ProductDTO productDTO) {
        return productService.save(productDTO);
    }

    /**
     * get product by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    /**
     * update
     *
     * @param id
     * @param productDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO) {
        if (!id.equals(productDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id doesn't match");
        }
        productService.update(id, productDTO);
    }

    /**
     * delete product type if there is no reference to
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }
}
