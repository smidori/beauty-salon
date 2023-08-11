package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.*;
import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.*;
import com.cct.beautysalon.services.BookService;
import com.cct.beautysalon.services.InvoiceService;
import com.cct.beautysalon.services.ProductService;
import com.cct.beautysalon.services.UserLoggedService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;


    /**
     * get invoice by filter
     *
     * @param filter
     * @return
     */
    @PostMapping("/withFilters")
    public List<InvoiceSummaryDTO> getInvoiceWithFilters(@RequestBody InvoiceFilterParamsDTO filter) {
        return invoiceService.findAllWithFilters(filter);
    }

    /**
     * saves the invoice
     *
     * @param invoiceDTO
     * @return
     */
    @PostMapping
    public InvoiceSummaryDTO save(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.save(invoiceDTO);
    }

    /**
     * get invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.findInvoiceByIdWithInvoiceItems(id);
    }

    /**
     * deletes the invoice
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        invoiceService.delete(id);
    }
}
