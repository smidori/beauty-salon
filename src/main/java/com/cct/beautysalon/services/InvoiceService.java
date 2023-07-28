package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Invoice;
import com.cct.beautysalon.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Iterable<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> findAllByCLientId(Long clientId) {
        return invoiceRepository.findAllByCLientId(clientId);
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Invoice by id "+ id+" not found"));
    }

    public Invoice findInvoiceByIdWithInvoiceItems(Long id) {
        return invoiceRepository.findInvoiceByIdWithInvoiceItems(id);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
    public void update(Long id, Invoice invoice) {
        findOrThrowInvoiceById(id);
        invoiceRepository.save(invoice);
    }

    private Invoice findOrThrowInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Invoice by id: " + id + "was not found"));
    }

    public void delete(Long id) {
        findOrThrowInvoiceById(id);
        invoiceRepository.deleteById(id);
    }
}
