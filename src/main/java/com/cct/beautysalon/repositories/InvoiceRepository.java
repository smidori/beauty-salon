package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.Invoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>  {
    @Query("SELECT i FROM Invoice i JOIN FETCH i.invoiceItems WHERE i.id = :id")
    Invoice findInvoiceByIdWithInvoiceItems(Long id);

    @Query("SELECT i FROM Invoice i JOIN FETCH i.invoiceItems WHERE i.client.id = :clientId")
    List<Invoice> findAllByCLientId(Long clientId);


    List<Invoice> findAll(Specification<Invoice> specification, Sort sort);
}
