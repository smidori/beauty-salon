package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>  {
    @Query("SELECT i FROM Invoice i JOIN FETCH i.invoiceItems WHERE i.id = :id")
    Invoice findInvoiceByIdWithInvoiceItems(Long id);
}
