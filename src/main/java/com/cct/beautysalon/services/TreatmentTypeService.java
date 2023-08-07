package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.TreatmentTypeDTO;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.TreatmentType;
import com.cct.beautysalon.repositories.TreatmentTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class TreatmentTypeService {
    private final TreatmentTypeRepository treatmentTypeRepository;
    private final ModelMapper mapper;

    private TreatmentTypeDTO toDTO(TreatmentType treatmentType) {
        return mapper.map(treatmentType, TreatmentTypeDTO.class);
    }

    private TreatmentType toEntity(TreatmentTypeDTO treatmentTypeDTO) {
        return mapper.map(treatmentTypeDTO, TreatmentType.class);
    }

    public List<TreatmentTypeDTO> findAll() {
        Sort sort = Sort.by("name").ascending();
        var treatmentTypes = StreamSupport.stream(treatmentTypeRepository.findAll(sort).spliterator(), false)
                .collect(Collectors.toList());
        return treatmentTypes.stream().map(this::toDTO).toList();
    }

    public TreatmentTypeDTO findTreatmentTypeById(Long id) {
        var treatment = treatmentTypeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment Type by id "+ id+" not found"));
        return toDTO(treatment);
    }

    public TreatmentTypeDTO save(TreatmentTypeDTO treatmentTypeDTO) {
        TreatmentType treatmentType = toEntity(treatmentTypeDTO);
        return toDTO(treatmentTypeRepository.save(treatmentType));
    }


    public void update(Long id, TreatmentTypeDTO treatmentTypeDTO) {
        findTreatmentTypeById(id);
        TreatmentType treatmentType = toEntity(treatmentTypeDTO);
        treatmentTypeRepository.save(treatmentType);
    }

    public void delete(Long id) {
        findTreatmentTypeById(id);
        treatmentTypeRepository.deleteById(id);
    }

}
