package com.cct.beautysalon.models;

import com.cct.beautysalon.DTO.ItemDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;

@Data //getters and setters
//@NoArgsConstructor //Without args constructor
//@AllArgsConstructor //with all args constructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "item_type")

public abstract class Item {
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

    /**
     * convert to entity
     * @param itemDTO
     * @return
     */
    public static Item toEntity(ItemDTO itemDTO) {
        Item item;
        switch (itemDTO.getItemType()) {
            case "Product":
                item = new Product(itemDTO.getId(), itemDTO.getName());
                break;
            case "Treatment":
                item = new Treatment(itemDTO.getId(), itemDTO.getName());
                break;
            default:
                throw new IllegalArgumentException("Invalid itemType: " + itemDTO.getItemType());
        }
        item.setId(itemDTO.getId());
        return item;
    }
}

