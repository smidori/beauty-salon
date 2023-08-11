package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.TreatmentSummaryDTO;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.repositories.TreatmentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    private final ModelMapper mapper;

    /**
     * convert to DTO
     *
     * @param treatment
     * @return
     */
    private TreatmentSummaryDTO toDTO(Treatment treatment) {
        TreatmentSummaryDTO dto = new TreatmentSummaryDTO();
        dto.setItemType("Treatment"); //Assign the type manually
        mapper.map(treatment, dto);
        return dto;
    }

    /**
     * convert to entity
     *
     * @param treatmentDTO
     * @return
     */
    private Treatment toEntity(TreatmentSummaryDTO treatmentDTO) {
        return mapper.map(treatmentDTO, Treatment.class);
    }

    /**
     * find all treatments
     *
     * @return
     */
    public List<TreatmentSummaryDTO> findAll() {
        Sort sort = Sort.by("name").ascending().and(Sort.by("type.name").ascending());


        var treatments = StreamSupport.stream(treatmentRepository.findAll(sort).spliterator(), false)
                .collect(Collectors.toList());
        return treatments.stream().map(this::toDTO).toList();

    }

    /**
     * find by id
     *
     * @param id
     * @return
     */
    public TreatmentSummaryDTO findTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment by id " + id + " not found"));
        return toDTO(treatment);
    }

    /**
     * save treatment
     *
     * @param treatmentDTO
     * @return
     */
    public TreatmentSummaryDTO save(TreatmentSummaryDTO treatmentDTO) {
        Treatment treatment = toEntity(treatmentDTO);
        Treatment saved = treatmentRepository.save(treatment);
        return toDTO(saved);
    }

    /**
     * update treatment
     *
     * @param id
     * @param treatmentDTO
     */
    public void update(Long id, TreatmentSummaryDTO treatmentDTO) {
        findOrThrowTreatmentById(id);
        Treatment treatment = toEntity(treatmentDTO);
        treatmentRepository.save(treatment);
    }

    /**
     * find or throw treatment
     *
     * @param id
     * @return
     */
    private Treatment findOrThrowTreatmentById(Long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Treatment by id: " + id + "was not found"));
    }

    /**
     * delete
     *
     * @param id
     */
    public void delete(Long id) {
        findOrThrowTreatmentById(id);
        treatmentRepository.deleteById(id);
    }
}
