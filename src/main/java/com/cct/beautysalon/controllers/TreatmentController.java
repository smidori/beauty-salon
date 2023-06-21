package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.TreatmentDTO;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.services.TreatmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;
    private final ModelMapper mapper;

    private TreatmentDTO toDTO(Treatment treatment) {
        return mapper.map(treatment, TreatmentDTO.class);
    }

    private Treatment toEntity(TreatmentDTO treatmentDTO) {
        return mapper.map(treatmentDTO, Treatment.class);
    }

    @GetMapping
    public List<TreatmentDTO> getTreatments() {
        var treatments = StreamSupport.stream(treatmentService.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        return treatments.stream().map(this::toDTO).toList();
    }


    public TreatmentDTO save(@Valid @RequestBody TreatmentDTO treatmentDTO) {
        Treatment treatment = toEntity(treatmentDTO);
        Treatment saved = treatmentService.save(treatment);
        return toDTO(saved);
    }

}
