package com.cct.beautysalon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String observation;
    private User worker;
    private int amount;
    private double subtotal;
    private double extra;
    private double discount;
    private double total;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", observation='" + observation + '\'' +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return amount == that.amount && Double.compare(that.subtotal, subtotal) == 0 && Double.compare(that.extra, extra) == 0 && Double.compare(that.discount, discount) == 0 && Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(observation, that.observation) && Objects.equals(worker, that.worker) && Objects.equals(book, that.book) && Objects.equals(item, that.item) && Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, observation, worker, amount, subtotal, extra, discount, total, book, item, invoice);
    }
}
