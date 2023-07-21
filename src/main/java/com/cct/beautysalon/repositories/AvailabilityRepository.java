package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Long>{

    @Query("SELECT a FROM Availability a JOIN FETCH a.treatments")
    List<Availability> findAllWithTreatments();

    @Query("SELECT a FROM Availability a JOIN a.treatments t WHERE t.id = :treatmentId and a.startDate <= :dateBook and a.finishDate is null")
    List<Availability> findByTreatmentId(long treatmentId, LocalDate dateBook);
}
