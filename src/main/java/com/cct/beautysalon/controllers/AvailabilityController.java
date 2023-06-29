package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.AvailabilityDTO;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.services.AvailabilityService;
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
        var availabilities = StreamSupport.stream(availabilityService.findAll().spliterator(), false)
                .collect(Collectors.toList());
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
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        availabilityService.delete(id);
    }
}
