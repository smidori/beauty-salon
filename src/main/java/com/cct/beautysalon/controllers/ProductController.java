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
    private final ModelMapper mapper;

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setItemType("Product"); //Assign the type manually
        mapper.map(product, dto);
        return dto;
    }

    private Product toEntity(ProductDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }

    @GetMapping
    public List<ProductDTO> getProducts() {
        var products = StreamSupport.stream(productService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return products.stream().map(this::toDTO).toList();
    }

    @PostMapping
    public ProductDTO save(@Valid @RequestBody ProductDTO productDTO) {
        Product product = toEntity(productDTO);
        Product saved = productService.save(product);
        return toDTO(saved);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable("id") Long id) {
        return toDTO(productService.findProductById(id));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO) {
        if(!id.equals(productDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id doesn't match");
        }
        Product product = toEntity(productDTO);
        productService.update(id, product);
    }

    /**
     * delete product type if there is no reference to
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try{
            productService.delete(id);
            return ResponseEntity.ok().build();
        }catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }
}
