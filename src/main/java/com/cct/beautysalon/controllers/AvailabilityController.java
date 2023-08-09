package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.AvailabilityDTO;
import com.cct.beautysalon.exceptions.AvailabilityConflictException;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.exceptions.StartDateAfterFinishDateException;
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

    @GetMapping
    public List<AvailabilityDTO> getAvailabilities() {
        return availabilityService.findAllWithTreatments();
    }

    /**
     * Create a new availability
     * @param ResponseEntity<Object>
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody AvailabilityDTO availabilityDTO) {
        try{
            return ResponseEntity.ok(availabilityService.save(availabilityDTO));
        }catch(AvailabilityConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }catch (StartDateAfterFinishDateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get Availability by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AvailabilityDTO getAvailabilityById(@PathVariable("id") Long id) {
        return availabilityService.findAvailabilityById(id);
    }

    /**
     * Update Availability by id
     * @param id
     * @param availabilityDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        try{
            if(!id.equals(availabilityDTO.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Availability id doesn't match");
            }
            availabilityService.update(id, availabilityDTO);
            return ResponseEntity.ok().build();
        }catch(AvailabilityConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }catch (StartDateAfterFinishDateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

    /**
     * Delete a Availability
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try{
            availabilityService.delete(id);
            return ResponseEntity.ok().build();
        }catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }
}
