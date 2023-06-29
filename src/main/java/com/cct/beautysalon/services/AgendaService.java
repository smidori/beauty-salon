package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Agenda;
import com.cct.beautysalon.repositories.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public Iterable<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findAgendaById(Long id) {
        return findOrThrowAgendaById(id);
    }

    public Agenda save(Agenda Agenda) {
        return agendaRepository.save(Agenda);
    }

    public void delete(Long id) {
        findOrThrowAgendaById(id);
        agendaRepository.deleteById(id);
    }

    public void update(Long id, Agenda Agenda) {
        findOrThrowAgendaById(id);
        agendaRepository.save(Agenda);
    }

    private Agenda findOrThrowAgendaById(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Agenda by id: " + id + "was not found"));
    }
}
