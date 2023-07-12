package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.AgendaDTO;
import com.cct.beautysalon.models.Agenda;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.services.AgendaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/agendas")
public class AgendaController {

    private final AgendaService agendaService;
    private final ModelMapper mapper;

    //convert the entity to DTO
    private AgendaDTO toDTO(Agenda agenda) {
        return mapper.map(agenda, AgendaDTO.class);
    }

    //convert the DTO to entity
    private Agenda toEntity(AgendaDTO agendaDTO) {
        return mapper.map(agendaDTO, Agenda.class);
    }
    @GetMapping
    public List<AgendaDTO> getAvailabilities() {
        var availabilities = StreamSupport.stream(agendaService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return availabilities.stream().map(this::toDTO).toList();
    }

    /**
     * Create a new agenda
     * @param AgendaDTO
     * @return
     */
    @PostMapping
    public AgendaDTO save(@Valid @RequestBody AgendaDTO AgendaDTO) {
        Agenda agenda = toEntity(AgendaDTO);
        Agenda agendaSaved = agendaService.save(agenda);
        return toDTO(agendaSaved);
    }

    /**
     * Get Agenda by id
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    public AgendaDTO getAgendaById(@PathVariable("id") Long id) {
//        return toDTO(agendaService.findAgendaById(id));
//    }

    @GetMapping("/{id}")
    public AgendaDTO getAgendaById(@PathVariable("id") Long id) {
        //Agenda agenda = agendaService.findAgendaById(id);

        LocalDate startDate = LocalDate.now(); // Data de início da agenda
        LocalDate endDate = LocalDate.now().plusDays(1); // Data de término da agenda (por exemplo, 7 dias à frente)
        LocalTime startTime = LocalTime.of(9, 0); // Horário de início do trabalho
        LocalTime endTime = LocalTime.of(17, 0); // Horário de término do trabalho

        List<LocalDateTime> availableDateTimeList = new ArrayList<>();

        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);


        LocalDateTime currentDateTime = LocalDateTime.of(startDate, startTime);


        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalDateTime currentStartDateTime = LocalDateTime.of(currentDate, startTime);
        LocalDateTime currentEndDateTime = LocalDateTime.of(currentDate, endTime);

        while (currentDateTime.isBefore(endDateTime)) {
            if(currentDateTime.toLocalDate().isAfter(currentDate)){
                currentStartDateTime = LocalDateTime.of(currentDate, startTime);
                currentEndDateTime = LocalDateTime.of(currentDate, endTime);
            }

            if( (currentDateTime.isAfter(currentStartDateTime) || currentDateTime.isEqual(currentStartDateTime)) &&
                    (currentDateTime.isBefore(currentEndDateTime) || currentDateTime.isEqual(currentEndDateTime))){
                System.out.println(currentStartDateTime + " <= " + currentDateTime + "<= " + currentEndDateTime);
                availableDateTimeList.add(currentDateTime);
            }
            currentDateTime = currentDateTime.plusMinutes(30);
        }

        Availability availability = new Availability();
        availability.setId(1L);
        for (LocalDateTime dateTime : availableDateTimeList) {
            // Salvar o registro no banco de dados
            Agenda agenda = new Agenda(availability,dateTime);
            agendaService.save(agenda);
        }


        return null;
    }

    /**
     * Update Agenda by id
     * @param id
     * @param agendaDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody AgendaDTO agendaDTO) {
        if(!id.equals(agendaDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agenda id doesn't match");
        }
        Agenda agenda = toEntity(agendaDTO);
        agendaService.update(id, agenda);
    }

    /**
     * Delete a Agenda
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        agendaService.delete(id);
    }
}
