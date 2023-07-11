package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.TreatmentType;
import com.cct.beautysalon.repositories.TreatmentTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TreatmentTypeService {
    private final TreatmentTypeRepository treatmentTypeRepository;

    public TreatmentTypeService(TreatmentTypeRepository treatmentTypeRepository) {
        this.treatmentTypeRepository = treatmentTypeRepository;
    }

    public Iterable<TreatmentType> findAll() {
        return treatmentTypeRepository.findAll();
    }

    public TreatmentType findTreatmentTypeById(Long id) {
        return treatmentTypeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment Type by id "+ id+" not found"));
    }

    public TreatmentType save(TreatmentType treatmentType) {
        return treatmentTypeRepository.save(treatmentType);
    }


    public void update(Long id, TreatmentType treatmentType) {
        findTreatmentTypeById(id);
        treatmentTypeRepository.save(treatmentType);
    }

    public void delete(Long id) {//TODO - CRIAR LÓGICA PARA NÃO PERMITIR EXCLUIR SE HOUVER AGENDAMENTO
        findTreatmentTypeById(id);
        treatmentTypeRepository.deleteById(id);
    }

}
