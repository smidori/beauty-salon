package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.TreatmentSummaryDTO;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.services.TreatmentService;
import com.cct.beautysalon.utils.ErrorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;
    private final ModelMapper mapper;

    private TreatmentSummaryDTO toDTO(Treatment treatment) {
        //return mapper.map(treatment, TreatmentSummaryDTO.class);
        TreatmentSummaryDTO dto = new TreatmentSummaryDTO();
        dto.setItemType("Treatment"); //Assign the type manually
        mapper.map(treatment, dto);
        return dto;
    }

    private Treatment toEntity(TreatmentSummaryDTO treatmentDTO) {

        return mapper.map(treatmentDTO, Treatment.class);
    }

    @GetMapping
    public List<TreatmentSummaryDTO> getTreatments() {
        var treatments = StreamSupport.stream(treatmentService.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        return treatments.stream().map(this::toDTO).toList();
    }

    @PostMapping
    public TreatmentSummaryDTO save(@Valid @RequestBody TreatmentSummaryDTO treatmentDTO) {
        Treatment treatment = toEntity(treatmentDTO);
        Treatment saved = treatmentService.save(treatment);
        return toDTO(saved);
    }

    @GetMapping("/{id}")
    public TreatmentSummaryDTO getTreatmentById(@PathVariable("id") Long id) {
        return toDTO(treatmentService.findTreatmentById(id));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody TreatmentSummaryDTO treatmentDTO) {
        if(!id.equals(treatmentDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment id doesn't match");
        }
        Treatment treatment = toEntity(treatmentDTO);
        treatmentService.update(id, treatment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try{
            treatmentService.delete(id);
            return ResponseEntity.ok().build();
        }catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }
}
