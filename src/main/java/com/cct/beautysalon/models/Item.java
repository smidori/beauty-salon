package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data //getters and setters
//@NoArgsConstructor //Without args constructor
//@AllArgsConstructor //with all args constructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "item_type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Treatment.class, name = "Treatment"),
//        @JsonSubTypes.Type(value = Product.class, name = "Product")
//})
//can't be abstract otherwise there is a problem to convert the itemDTO to item
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String description;
    protected double price;

    // Construtor protegido com a anotação @JsonCreator
    @JsonCreator
    protected Item() {
        // Construtor vazio, pode ser protegido, pois a classe é abstrata e não será instanciada diretamente
    }

//    public Item(String name, String description, double price) {
//        this.name = name;
//        this.description = description;
//        this.price = price;
//    }

}

