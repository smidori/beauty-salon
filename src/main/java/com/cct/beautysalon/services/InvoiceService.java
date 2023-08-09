package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.*;
import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.*;
import com.cct.beautysalon.repositories.InvoiceRepository;
import com.cct.beautysalon.repositories.specifications.BookSpecifications;
import com.cct.beautysalon.repositories.specifications.InvoiceSpecifications;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;
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

    private Invoice toEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = InvoiceDTO.toEntity(invoiceDTO);
        invoice.setInvoiceItems(new HashSet<>());

        if (invoiceDTO.getInvoiceItems() != null) {
            for (InvoiceItemDTO invoiceItemDTO : invoiceDTO.getInvoiceItems()) {
                InvoiceItem invoiceItem = InvoiceItemDTO.toEntity(invoiceItemDTO);
                invoice.addInvoiceItem(invoiceItem);
            }
        }
        return invoice;
    }

    public Iterable<Invoice> findAll() {
        Sort sort = Sort.by("date").ascending().and(Sort.by("client.firstName").ascending()).and(Sort.by("client.lastName").ascending());
        return invoiceRepository.findAll();
    }

    public List<InvoiceSummaryDTO> findAllWithFilters(InvoiceFilterParamsDTO filter) {

        User userLogged = userLoggedService.getUserLogged();
        List<Invoice> invoices = new ArrayList<>();

        if(userLogged.getRole().equals(Role.CLIENT)){
            filter.setClientId(userLogged.getId());
        }

        Specification<Invoice> spec = InvoiceSpecifications.withFilters(filter.getDateBook(),
                filter.getClientId(),
                filter.getFilterDateBy());
        Sort sort = Sort.by("date", "client.firstName", "client.lastName").ascending();


        invoices.addAll(StreamSupport.stream(invoiceRepository.findAll(spec, sort).spliterator(), false).collect(Collectors.toList()));

        return invoices.stream()
                .map(this::toSummaryDTO)
                .collect(Collectors.toList());
    }


    public List<Invoice> findAllByCLientId(Long clientId) {
        return invoiceRepository.findAllByCLientId(clientId);
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Invoice by id "+ id+" not found"));
    }

    public InvoiceDTO findInvoiceByIdWithInvoiceItems(Long id) {
        return toDTO(invoiceRepository.findInvoiceByIdWithInvoiceItems(id));
    }

    public InvoiceSummaryDTO save(InvoiceDTO invoiceDTO) {
        Invoice invoice = toEntity(invoiceDTO);
        invoice.setCreatedDate(LocalDateTime.now());
        invoice.setDate(LocalDateTime.now().toLocalDate());
        Invoice invoiceSaved = invoiceRepository.save(invoice);

        //check if the products were selected and update the stock information
        System.out.println("invoiceItem saved: " + invoiceSaved.getId());

        var inv = this.findInvoiceById(invoiceSaved.getId());
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

        //return invoiceRepository.save(invoice);
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
