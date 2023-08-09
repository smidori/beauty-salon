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
    private final BookService bookService;
    private final UserLoggedService userLoggedService;
    private final ProductService productService;
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

//    private Invoice toEntity(InvoiceDTO invoiceDTO) {
//        System.out.println("aaaaaaaaaaaaaaaaaaa");
//        Invoice invoice = mapper.map(invoiceDTO, Invoice.class);
//        System.out.println("bbbbbbbbbbbbbbb");
//        invoice.setInvoiceItems(new HashSet<>());
//        // Add InvoiceItem to Invoice, to be able to persistAll and set the parent invoice
//        if (invoiceDTO.getInvoiceItems() != null) {
//            for (InvoiceItemDTO invoiceItemDTO : invoiceDTO.getInvoiceItems()) {
//                System.out.println("ccccccccccc");
//                InvoiceItem item = mapper.map(invoiceItemDTO, InvoiceItem.class);
//                item.setItem(Item.fromDTO(invoiceItemDTO.getItem()));
//                invoice.addInvoiceItem(item);
//            }
//        }
//        return invoice;
//    }

    private Invoice toEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = InvoiceDTO.toEntity(invoiceDTO);
        invoice.setInvoiceItems(new HashSet<>());

        if (invoiceDTO.getInvoiceItems() != null) {
            for (InvoiceItemDTO invoiceItemDTO : invoiceDTO.getInvoiceItems()) {
                InvoiceItem invoiceItem = InvoiceItemDTO.toEntity(invoiceItemDTO);
                invoice.addInvoiceItem(invoiceItem);
            }
        }

        //invoice.setInvoiceItems(new HashSet<>(invoiceItems));

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
        Invoice invoiceSaved = invoiceService.save(invoice);

        //check if the products were selected and update the stock information
        System.out.println("invoiceItem saved: " + invoiceSaved.getId());

        var inv = invoiceService.findInvoiceById(invoiceSaved.getId());
                //.findInvoiceByIdWithInvoiceItems(saved.getId());

        System.out.println("--------- invoiceItems = " + inv.getInvoiceItems().size());

        //update the stock information
        inv.getInvoiceItems().stream().filter(invoiceItem ->
                invoiceItem.getItem() instanceof Product)
                .forEach(invoiceItem -> {
                    Product product = productService.findProductById(invoiceItem.getItem().getId());
                    product.setStock(product.getStock() - invoiceItem.getAmount());
                    productService.update(product.getId(), product);
                });

        //check which treatment is billed and change the status
        inv.getInvoiceItems().stream().filter(invoiceItem -> invoiceItem.getBook() != null)
        .forEach(invoiceItem -> {

            Book book = invoiceItem.getBook();
            bookService.updateStatusDescription(book.getId(), BookStatus.BILLED, book.getObservation());

            //book.setStatus(BookStatus.BILLED);
            //book.setInServiceDate(LocalDateTime.now());
            //bookService.update(book.getId(), book);
        });
        return toSummaryDTO(invoiceSaved);
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        invoiceService.delete(id);
    }
}
