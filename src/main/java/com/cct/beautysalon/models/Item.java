package com.cct.beautysalon.models;

import com.cct.beautysalon.DTO.ItemDTO;
import com.cct.beautysalon.DTO.ProductDTO;
import com.cct.beautysalon.DTO.TreatmentSummaryDTO;
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

    public static Item fromDTO(ItemDTO itemDTO) {
        Item item;
        switch (itemDTO.getItemType()) {
//                    case "Product":
//                        item = new Product();
//                        ProductDTO productDTO = (ProductDTO) itemDTO;
//                        item.setId(productDTO.getId());
//                        item.setName(productDTO.getName());
//                        item.setDescription(productDTO.getDescription());
//                        item.setPrice(productDTO.getPrice());
//                        ((Product) item).setStock(productDTO.getStock());
//                        break;
//                    case "Treatment":
//                        item = new Treatment();
//                        TreatmentSummaryDTO treatmentDTO = (TreatmentSummaryDTO) itemDTO;
//                        item.setId(treatmentDTO.getId());
//                        item.setName(treatmentDTO.getName());
//                        item.setDescription(treatmentDTO.getDescription());
//                        item.setPrice(treatmentDTO.getPrice());
//                        ((Treatment) item).setType(treatmentDTO.getType());
//                        ((Treatment) item).setDuration(treatmentDTO.getDuration());
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Invalid itemType: " + itemDTO.getItemType());


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

