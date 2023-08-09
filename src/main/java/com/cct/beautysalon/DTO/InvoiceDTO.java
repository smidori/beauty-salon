package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Invoice;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDTO {
    private Long id;
    private UserSummaryDTO client;
    private String observation;
    private double total;
    private LocalDateTime createdDate;
    private Set<InvoiceItemDTO> invoiceItems;
    private LocalDate date;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDTO that = (InvoiceDTO) o;
        return Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(client, that.client) && Objects.equals(observation, that.observation) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, observation, total, date);
    }

    public static Invoice toEntity(InvoiceDTO dto){
        if(dto == null)
            return null;
        return Invoice.builder().id(dto.getId())
                .client(UserSummaryDTO.toEntity(dto.getClient()))
                .observation(dto.getObservation())
                .total(dto.getTotal())
                .date(dto.getDate())
                .createdDate(dto.getCreatedDate())
                .build();
    }
}
