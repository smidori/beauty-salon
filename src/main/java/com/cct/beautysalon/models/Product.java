package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data //getters and setters
//@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
@DiscriminatorValue("Product")
public class Product extends Item{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String description;
//    private double price;

//    public Product(String name, String description, double price) {
//        super.name = name;
//        super.description = description;
//        super.price = price;
//    }

    public Product(String name, String description, double price) {
        super(name, description, price);
    }
}
