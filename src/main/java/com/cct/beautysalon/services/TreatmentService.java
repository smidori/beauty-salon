package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public Iterable<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    public Treatment findTreatmentById(Long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment by id "+ id+" not found"));
    }

    public Treatment save(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }
    public void update(Long id, Treatment treatment) {
        findOrThrowTreatmentById(id);
        treatmentRepository.save(treatment);
    }

    private Treatment findOrThrowTreatmentById(Long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment by id: " + id + "was not found"));
    }

    public void delete(Long id) {
        findOrThrowTreatmentById(id);
        treatmentRepository.deleteById(id);
    }
}
