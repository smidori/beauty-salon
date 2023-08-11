package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
@DiscriminatorValue("Product")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "item_type", visible = true)
public class Product extends Item{
    private int stock;
    //constructors
    public Product(String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(long id, String name){
        this.id = id;
        this.name = name;
    }
}
