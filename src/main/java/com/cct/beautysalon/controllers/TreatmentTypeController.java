package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.TreatmentTypeDTO;
import com.cct.beautysalon.models.TreatmentType;
import com.cct.beautysalon.services.TreatmentTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/treatment-types")
public class TreatmentTypeController {

    private final TreatmentTypeService treatmentTypeService;
    private final ModelMapper mapper;

    private TreatmentTypeDTO toDTO(TreatmentType treatmentType) {
        return mapper.map(treatmentType, TreatmentTypeDTO.class);
    }

    private TreatmentType toEntity(TreatmentTypeDTO treatmentTypeDTO) {
        return mapper.map(treatmentTypeDTO, TreatmentType.class);
    }

    @GetMapping
    public List<TreatmentTypeDTO> getTreatmentTypes() {
        var treatmentTypes = StreamSupport.stream(treatmentTypeService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return treatmentTypes.stream().map(this::toDTO).toList();
    }

    @PostMapping
    public TreatmentTypeDTO save(@Valid @RequestBody TreatmentTypeDTO treatmentTypeDTO) {
        TreatmentType treatmentType = toEntity(treatmentTypeDTO);
        TreatmentType saved = treatmentTypeService.save(treatmentType);
        return toDTO(saved);
    }

    @GetMapping("/{id}")
    public TreatmentTypeDTO getTreatmentTypeById(@PathVariable("id") Long id) {
        return toDTO(treatmentTypeService.findTreatmentTypeById(id));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody TreatmentTypeDTO treatmentTypeDTO) {
        if(!id.equals(treatmentTypeDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TreatmentType id doesn't match");
        }
        TreatmentType treatmentType = toEntity(treatmentTypeDTO);
        treatmentTypeService.update(id, treatmentType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        treatmentTypeService.delete(id);
    }
}
