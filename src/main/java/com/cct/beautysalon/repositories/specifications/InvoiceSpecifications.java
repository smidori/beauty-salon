package com.cct.beautysalon.repositories.specifications;

import com.cct.beautysalon.models.Invoice;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class InvoiceSpecifications {

    public static Specification<Invoice> withFilters(LocalDate dateBook, Long clientUser, String filterDateBy) {
        Specification<Invoice> spec = Specification.where(null);
        System.out.println("Invoice specification clientUser => " + clientUser);
        System.out.println("Invoice specification dateBook => " + dateBook);

        if (dateBook != null) {
            if(filterDateBy == null || filterDateBy.equals("=")){
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), dateBook));
            }else if(filterDateBy.equals(">=")){
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dateBook));
            }else{
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), dateBook));
            }
        }

        if (clientUser != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("client").get("id"), clientUser));
        }

        return spec;
    }
}

