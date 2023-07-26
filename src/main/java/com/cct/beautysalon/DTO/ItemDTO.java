package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    protected Long id;
    protected String name;
    protected String description;
    protected double price;
    protected String itemType;
}
