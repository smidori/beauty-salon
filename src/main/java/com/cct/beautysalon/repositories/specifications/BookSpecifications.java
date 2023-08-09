package com.cct.beautysalon.repositories.specifications;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecifications {

    public static Specification<Book> withFilters(LocalDate dateBook, BookStatus status, Long clientUser, Long workerUser,String filterDateBy) {
        Specification<Book> spec = Specification.where(null);

        if (dateBook != null) {
            if(filterDateBy == null || filterDateBy.equals("=")){
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateBook"), dateBook));
            }else if(filterDateBy.equals(">=")){
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateBook"), dateBook));
            }else{
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateBook"), dateBook));
            }
        }

        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
        }

        if (clientUser != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("clientUser").get("id"), clientUser));
        }

        if (workerUser != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("workerUser").get("id"), workerUser));
        }

        return spec;
    }
}

