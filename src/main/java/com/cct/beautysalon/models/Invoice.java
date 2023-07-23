package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User client;
    private String observation;
    private double total;
    private LocalDateTime date;

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "invoice_item",
//            joinColumns = @JoinColumn(name = "availability_id"),
//            inverseJoinColumns = @JoinColumn(name = "treatment_id")
//    )
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvoiceItem> itens;



}
