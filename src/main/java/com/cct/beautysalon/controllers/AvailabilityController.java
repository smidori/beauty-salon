package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.AvailabilityDTO;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.services.AvailabilityService;
import com.cct.beautysalon.utils.ErrorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final ModelMapper mapper;

    //convert the entity to DTO
    private AvailabilityDTO toDTO(Availability availability) {
        return mapper.map(availability, AvailabilityDTO.class);
    }

    //convert the DTO to entity
    private Availability toEntity(AvailabilityDTO availabilityDTO) {
        return mapper.map(availabilityDTO, Availability.class);
    }
    @GetMapping
    public List<AvailabilityDTO> getAvailabilities() {
        var availabilities = StreamSupport.stream(availabilityService.findAllWithTreatments().spliterator(), false)
                .collect(Collectors.toList());

        // Print treatments before conversion
//        availabilities.forEach(availability -> {
//            System.out.println("Availability ID: " + availability.getId());
//            availability.getTreatments().forEach(treatment -> System.out.println("Treatment ID: " + treatment.getId()));
//        });

        return availabilities.stream().map(this::toDTO).toList();
    }

    /**
     * Create a new availability
     * @param AvailabilityDTO
     * @return
     */
    @PostMapping
    public AvailabilityDTO save(@Valid @RequestBody AvailabilityDTO AvailabilityDTO) {
        Availability availability = toEntity(AvailabilityDTO);
        Availability availabilitySaved = availabilityService.save(availability);
        return toDTO(availabilitySaved);
    }

    /**
     * Get Availability by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AvailabilityDTO getAvailabilityById(@PathVariable("id") Long id) {
        return toDTO(availabilityService.findAvailabilityById(id));
    }

    /**
     * Update Availability by id
     * @param id
     * @param availabilityDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        if(!id.equals(availabilityDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Availability id doesn't match");
        }
        Availability availability = toEntity(availabilityDTO);
        availabilityService.update(id, availability);
    }

    /**
     * Delete a Availability
     * @param id
     */
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable("id") Long id) {
//        availabilityService.delete(id);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
//        try{
//            availabilityService.delete(id);
//            return ResponseEntity.ok().build();
//        }catch(NotFoundException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
//        }
//    }
}
