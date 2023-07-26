package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>  {

}
