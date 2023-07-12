package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.repositories.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    public Iterable<Availability> findAll() {
        return availabilityRepository.findAll();
        //return availabilityRepository.findAllWithTreatments();
    }

    public Availability findAvailabilityById(Long id) {
        return findOrThrowAvailabilityById(id);
    }

    public Availability save(Availability Availability) {
        return availabilityRepository.save(Availability);
    }

    public void delete(Long id) {
        findOrThrowAvailabilityById(id);
        availabilityRepository.deleteById(id);
    }

    public void update(Long id, Availability Availability) {
        findOrThrowAvailabilityById(id);
        availabilityRepository.save(Availability);
    }

    private Availability findOrThrowAvailabilityById(Long id) {
        return availabilityRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Availability by id: " + id + "was not found"));
    }

    public List<Availability> findByTreatmentId(long treatmentId){
        return availabilityRepository.findByTreatmentId(treatmentId);
    }

}
