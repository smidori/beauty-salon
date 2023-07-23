package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.Invoice;
import com.cct.beautysalon.models.Item;
import com.cct.beautysalon.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceItemDTO {
    private Long id;
    private String description;
    private String observation;
    private UserDTO worker;
    private int amount;
    private double subtotal;
    private double extra;
    private double discount;
    private double total;
    private BookDTO book;
    private Item item;
    private Invoice invoice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItemDTO that = (InvoiceItemDTO) o;
        return amount == that.amount && Double.compare(that.subtotal, subtotal) == 0 && Double.compare(that.extra, extra) == 0 && Double.compare(that.discount, discount) == 0 && Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(observation, that.observation) && Objects.equals(worker, that.worker) && Objects.equals(book, that.book) && Objects.equals(item, that.item) && Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, observation, worker, amount, subtotal, extra, discount, total, book, item, invoice);
    }
}
