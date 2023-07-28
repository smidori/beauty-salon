package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.InvoiceDTO;
import com.cct.beautysalon.DTO.InvoiceItemDTO;
import com.cct.beautysalon.DTO.InvoiceSummaryDTO;
import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.Invoice;
import com.cct.beautysalon.models.InvoiceItem;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.services.BookService;
import com.cct.beautysalon.services.InvoiceService;
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
    private final BookService bookService;
    private final UserLoggedService userLoggedService;

    private final ModelMapper mapper;

    private InvoiceSummaryDTO toSummaryDTO(Invoice invoice) {
        return mapper.map(invoice, InvoiceSummaryDTO.class);
    }

    private InvoiceDTO toDTO(Invoice invoice) {
        return mapper.map(invoice, InvoiceDTO.class);
    }

    private Invoice toEntity(InvoiceSummaryDTO invoiceDTO) {
        return mapper.map(invoiceDTO, Invoice.class);
    }

//    private Invoice toEntity(InvoiceDTO invoiceDTO) {
//        return mapper.map(invoiceDTO, Invoice.class);
//    }

    private Invoice toEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = mapper.map(invoiceDTO, Invoice.class);
        invoice.setInvoiceItems(new HashSet<>());
        // Add InvoiceItem to Invoice, to be able to persistAll and set the parent invoice
        if (invoiceDTO.getInvoiceItems() != null) {
            for (InvoiceItemDTO itemDTO : invoiceDTO.getInvoiceItems()) {
                InvoiceItem item = mapper.map(itemDTO, InvoiceItem.class);
                invoice.addInvoiceItem(item);
            }
        }
        return invoice;
    }

    @GetMapping
    public List<InvoiceSummaryDTO> getInvoices() {
        User userLogged = userLoggedService.getUserLogged();
        List<Invoice> invoices = new ArrayList<>();

        switch (userLogged.getRole()) {
            case ADMIN, WORKER:
                invoices.addAll(StreamSupport.stream(invoiceService.findAll().spliterator(), false)
                        .collect(Collectors.toList()));
                break;
            case CLIENT:
                invoices.addAll(invoiceService.findAllByCLientId(userLogged.getId()));
                //sort in order descending
                invoices.sort(Comparator.comparing(Invoice::getDate).reversed());
                break;
            default:
                // Caso a role não corresponda a nenhuma das opções acima, retornar uma lista vazia.
                return new ArrayList<>();
        }
        return invoices.stream().map(this::toSummaryDTO).toList();
    }

    @PostMapping
    public InvoiceSummaryDTO save(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = toEntity(invoiceDTO);
        invoice.setDate(LocalDateTime.now());
        Invoice saved = invoiceService.save(invoice);

        //check which treatment is billed and change the status
        saved.getInvoiceItems().stream().filter(invoiceItem -> invoiceItem.getBook() != null)
        .forEach(invoiceItem -> {
            Book book = invoiceItem.getBook();
            book.setStatus(BookStatus.BILLED);
            book.setUpdatedDate(LocalDateTime.now());
            bookService.update(book.getId(), book);
        });
        return toSummaryDTO(saved);
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable("id") Long id) {
        return toDTO(invoiceService.findInvoiceByIdWithInvoiceItems(id));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody InvoiceSummaryDTO invoiceDTO) {
        if(!id.equals(invoiceDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice id doesn't match");
        }
        Invoice invoice = toEntity(invoiceDTO);
        invoiceService.update(id, invoice);
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable("id") Long id) {
//        invoiceService.delete(id);
//    }
}
