package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Long>{

    @Query("SELECT  a FROM Availability a JOIN FETCH a.treatments t ORDER BY a.user.firstName ASC," +
            "a.user.lastName ASC, a.startDate ASC, a.hourStartTime, t.name ASC")
    List<Availability> findAllWithTreatments();

    @Query("SELECT a FROM Availability a JOIN a.treatments t WHERE t.id = :treatmentId and ((a.startDate <= :dateBook and a.finishDate is null) or (a.startDate <= :dateBook and a.finishDate >= :dateBook ) )")
    List<Availability> findByTreatmentId(long treatmentId, LocalDate dateBook);

    @Query("SELECT distinct a FROM Availability a JOIN a.treatments t "
            + " WHERE a.user.id = :userId "
            + "and ( "
                + "( "
                + "(a.startDate <= :startDate and a.finishDate >= :startDate) "
                + " or (a.startDate <= :finishDate and a.finishDate >= :startDate) "
            + "or a.startDate = :startDate or a.startDate = :finishDate "
            + "or a.finishDate = :startDate or a.finishDate = :finishDate "
                + " ) "
                + "and ( "
               + " ( "
                + " (a.hourStartTime <= :hourStartTime and a.hourFinishTime > :hourStartTime) "
                + "or (a.hourStartTime <= :hourFinishTime and a.hourFinishTime > :hourFinishTime) "
                + " ) or ("
            + " (:hourStartTime <= a.hourStartTime  and  :hourFinishTime > a.hourStartTime ) "
            + "or ( :hourFinishTime >= a.hourStartTime and  :hourFinishTime < a.hourFinishTime) "
            + " ) "
            + " )"
            + ") "
    )
    List<Availability> findConflicting(long userId, LocalDate startDate, LocalDate finishDate, Time hourStartTime, Time hourFinishTime);
}
