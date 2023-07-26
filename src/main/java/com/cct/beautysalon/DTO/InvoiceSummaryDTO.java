package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceSummaryDTO {
    private Long id;
    private UserDTO client;
    private String observation;
    private double total;
    private LocalDateTime date;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceSummaryDTO that = (InvoiceSummaryDTO) o;
        return Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(client, that.client) && Objects.equals(observation, that.observation) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, observation, total, date);
    }
}
