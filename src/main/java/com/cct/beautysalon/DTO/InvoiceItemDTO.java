package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceItemDTO {
    private Long id;
    private String description;
    private String observation;
    private int amount;
    private double subtotal;
    private double extra;
    private double discount;
    private double total;
    private BookDTO book;
    private ItemDTO item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItemDTO that = (InvoiceItemDTO) o;
        return amount == that.amount && Double.compare(that.subtotal, subtotal) == 0 && Double.compare(that.extra, extra) == 0 && Double.compare(that.discount, discount) == 0 && Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(observation, that.observation) && Objects.equals(book, that.book) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, observation, amount, subtotal, extra, discount, total, book, item);
    }

    /**
     * convert to entity
     * @param dto
     * @return
     */
    public static InvoiceItem toEntity(InvoiceItemDTO dto){
        if(dto == null)
            return null;
        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setId(dto.getId());
        invoiceItem.setDescription(dto.getDescription());
        invoiceItem.setObservation(dto.getObservation());
        invoiceItem.setAmount(dto.getAmount());
        invoiceItem.setSubtotal(dto.getSubtotal());
        invoiceItem.setExtra(dto.getExtra());
        invoiceItem.setDiscount(dto.getDiscount());
        invoiceItem.setTotal(dto.getTotal());
        if(dto.getBook() != null){
            invoiceItem.setBook(dto.getBook().toEntity());
        }

        Item item = Item.toEntity(dto.getItem());
        invoiceItem.setItem(item);
        return invoiceItem;
    }
}
