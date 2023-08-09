package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    private String observation;
    private double total;
    private LocalDateTime createdDate;
    private LocalDate date;

    //@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

//    public void updateInvoiceReference() {
//        if (invoiceItems != null) {
//            invoiceItems.forEach(item -> item.setInvoice(this));
//        }
//    }


    public void addInvoiceItem(InvoiceItem item) {
        invoiceItems.add(item);
        item.setInvoice(this);
    }

//    public void removeInvoiceItem(InvoiceItem item) {
//        invoiceItems.remove(item);
//        item.setInvoice(null);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.total, total) == 0 && Objects.equals(id, invoice.id) && Objects.equals(client, invoice.client) && Objects.equals(observation, invoice.observation) && Objects.equals(date, invoice.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, observation, total, date);
    }
}
