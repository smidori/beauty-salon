package com.cct.beautysalon.repositories.specifications;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecifications {

    public static Specification<Book> withFilters(LocalDate dateBook, BookStatus status, Long clientUser, Long workerUser) {
        Specification<Book> spec = Specification.where(null);

        if (dateBook != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateBook"), dateBook));
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

