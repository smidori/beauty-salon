package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.InvoiceDTO;
import com.cct.beautysalon.DTO.InvoiceItemDTO;
import com.cct.beautysalon.DTO.InvoiceSummaryDTO;
import com.cct.beautysalon.models.Invoice;
import com.cct.beautysalon.models.InvoiceItem;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.services.InvoiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
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
        // Verifique se há InvoiceItems no InvoiceDTO e adicione-os à instância de Invoice
        if (invoiceDTO.getInvoiceItems() != null) {
            for (InvoiceItemDTO itemDTO : invoiceDTO.getInvoiceItems()) {
                InvoiceItem item = mapper.map(itemDTO, InvoiceItem.class);
//                InvoiceItem item = new InvoiceItem();
//                item.setAmount(itemDTO.getAmount());
//                item.setSubtotal(itemDTO.getSubtotal());
//                item.setTotal(itemDTO.getTotal());
//                if(itemDTO.getWorker() != null){
//                    item.setWorker(mapper.map(itemDTO.getWorker(), User.class));
//                }

                invoice.addInvoiceItem(item);
            }
        }

        return invoice;
    }








    @GetMapping
    public List<InvoiceSummaryDTO> getInvoices() {
        var invoices = StreamSupport.stream(invoiceService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return invoices.stream().map(this::toSummaryDTO).toList();
    }

    @PostMapping
    public InvoiceSummaryDTO save(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = toEntity(invoiceDTO);
        invoice.setDate(LocalDateTime.now());
        //invoice.updateInvoiceReference();
        System.out.println("Invoice => " + invoice.toString());
        Invoice saved = invoiceService.save(invoice);
        return toSummaryDTO(saved);
    }

    @GetMapping("/{id}")
    public InvoiceSummaryDTO getInvoiceById(@PathVariable("id") Long id) {
        return toSummaryDTO(invoiceService.findInvoiceById(id));
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
