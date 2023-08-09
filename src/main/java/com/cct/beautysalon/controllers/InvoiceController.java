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





//    @GetMapping
//    public List<InvoiceSummaryDTO> getInvoices() {
//        User userLogged = userLoggedService.getUserLogged();
//        List<Invoice> invoices = new ArrayList<>();
//
//        switch (userLogged.getRole()) {
//            case ADMIN, WORKER:
//                invoices.addAll(StreamSupport.stream(invoiceService.findAll().spliterator(), false)
//                        .collect(Collectors.toList()));
//                break;
//            case CLIENT:
//                invoices.addAll(invoiceService.findAllByCLientId(userLogged.getId()));
//
//                //sort in order descending
//                invoices.sort(Comparator.comparing(Invoice::getDate).reversed());
//                break;
//            default:
//                // Caso a role não corresponda a nenhuma das opções acima, retornar uma lista vazia.
//                return new ArrayList<>();
//        }
//        return invoices.stream().map(this::toSummaryDTO).toList();
//    }

    @PostMapping("/withFilters")
    public List<InvoiceSummaryDTO> getInvoiceWithFilters(@RequestBody InvoiceFilterParamsDTO filter) {
        return invoiceService.findAllWithFilters(filter);
    }

    @PostMapping
    public InvoiceSummaryDTO save(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.save(invoiceDTO);
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.findInvoiceByIdWithInvoiceItems(id);
    }

//    @PutMapping("/{id}")
//    public void update(@PathVariable("id") Long id, @Valid @RequestBody InvoiceSummaryDTO invoiceDTO) {
//        if(!id.equals(invoiceDTO.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice id doesn't match");
//        }
//        Invoice invoice = toEntity(invoiceDTO);
//        invoiceService.update(id, invoice);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        invoiceService.delete(id);
    }
}
