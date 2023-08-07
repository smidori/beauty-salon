package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.AvailabilityDTO;
import com.cct.beautysalon.exceptions.AvailabilityConflictException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.repositories.AvailabilityRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ModelMapper mapper;

    //convert the entity to DTO
    private AvailabilityDTO toDTO(Availability availability) {
        return mapper.map(availability, AvailabilityDTO.class);
    }

    //convert the DTO to entity
    private Availability toEntity(AvailabilityDTO availabilityDTO) {
        return mapper.map(availabilityDTO, Availability.class);
    }

    public List<AvailabilityDTO> findAllWithTreatments() {
        var availabilities = availabilityRepository.findAllWithTreatments();
        return availabilities.stream().map(this::toDTO).toList();
        //return availabilityRepository.findAllWithTreatments();
    }

    public AvailabilityDTO findAvailabilityById(Long id) {
        return toDTO(findOrThrowAvailabilityById(id));
    }

    public AvailabilityDTO save(AvailabilityDTO dto) {
        Availability availability = toEntity(dto);
        try{
            validatePeriodAvailability(dto);
            return toDTO(availabilityRepository.save(availability));
        }catch (AvailabilityConflictException e){
            throw new AvailabilityConflictException(e.getMessage());
        }

    }
/*
    private void validatePeriodAvailability(AvailabilityDTO dto) {
        System.out.println("***************** validatePeriodAvailability");
        //check if there is conflicting with another availability for this user
        List<Availability> conflicting = availabilityRepository.findConflicting(dto.getUser().getId(), dto.getStartDate(), dto.getFinishDate(), dto.getHourStartTime(), dto.getHourFinishTime());
        if(conflicting.size() == 0){
            System.out.println("conflict size is 0");
            return ;
        } else if(conflicting.size() == 1){
            System.out.println("conflict size is 1");
            Availability availBD = conflicting.get(0);
            if(availBD.getFinishDate() == null){
                //new register or different register
                //if(dto.getId() == null || !dto.getId().equals(availBD.getId())){
                    //LocalDate supposedFinishDate = dto.getFinishDate().plusDays(-1);
                    //System.out.println("new finish date is " + availBD.getFinishDate());

                    //start date saved is after finish date new/update
                    if(availBD.getStartDate().isAfter(dto.getFinishDate())) {
                        System.out.println("start date saved is after finish date new/update");
                        return;
                    }else if(availBD.getStartDate().isBefore(dto.getFinishDate()) &&
                            (availBD.getStartDate().isAfter(dto.getStartDate()) ||
                                    availBD.getStartDate().isEqual(dto.getStartDate())
                    )){
                        throw new AvailabilityConflictException("Conflict with id = x " + availBD.getId());
                    }else if((availBD.getStartDate().isBefore(dto.getStartDate())
                    || (availBD.getStartDate().isEqual(dto.getStartDate())) &&
                            (availBD.getFinishDate().isAfter(dto.getStartDate()))
                            )){
                        throw new AvailabilityConflictException("Conflict with id = y " + availBD.getId());
                    }else if(dto.getStartDate().isAfter(availBD.getStartDate())){
                        LocalDate supposedFinishDate = dto.getFinishDate().plusDays(-1);
                        System.out.println("new finish date is " + supposedFinishDate);
                        availBD.setFinishDate(supposedFinishDate);
                    }else if(availBD.getStartDate().isEqual(dto.getFinishDate())){
                        System.out.println("bd start date = dto finish date");
                        validateTime(dto, availBD);
                    }
                //}//null date but same register
//                if(dto.getFinishDate() == null && (dto.getId() != null && dto.getId().equals(availBD.getId()))) {
//                    System.out.println("null date but same register");
//                    //DO nothing
//                    //validateTime(dto, availBD);
//                    //can't create ANOTHER availability with EMPTY finish date
//                    //throw new AvailabilityConflictException("can't create ANOTHER availability with EMPTY finish date");
//                }else if(dto.getFinishDate() == null && !(dto.getId() != null && dto.getId().equals(availBD.getId()))){
//                    throw new AvailabilityConflictException("can't create ANOTHER availability with EMPTY finish date");
//                }

            }else if(dto.getId() != null && dto.getId().equals(conflicting.get(0).getId())){
                System.out.println("mesmo registro e passou por outras validações");
                return ;
            }else{
                throw new AvailabilityConflictException("Conflict with id = e " + conflicting.get(0).getId());
            }
            //check if it is null and set date finish to date start -1
        }else{
            System.out.println("conflict size bigger than  1");
            String ids = "";
            for (Availability av : conflicting) {
                if(dto.getId() != null && dto.getId().equals(av.getId())){
                    continue;
                }
                ids += ", " + av.getId();
            }
            ids = ids.replaceFirst(", ","");
            //return error
            throw new AvailabilityConflictException("Conflict with ids = " + ids);
        }
    }
*/
private void validatePeriodAvailability(AvailabilityDTO dto) {
    System.out.println("***************** validatePeriodAvailability");
    //check if there is conflicting with another availability for this user
    List<Availability> conflicting = availabilityRepository.findConflicting(dto.getUser().getId(), dto.getStartDate(), dto.getFinishDate(), dto.getHourStartTime(), dto.getHourFinishTime());
    if(conflicting.size() == 0){
        System.out.println("conflict size is 0");
        return ;
    } else if(conflicting.size() == 1){
        System.out.println("conflict size is 1");
        Availability availBD = conflicting.get(0);
            if(dto.getId() != null && dto.getId().equals(availBD.getId())){
                //faz nada
            }
            else if(availBD.getStartDate().isAfter(dto.getFinishDate())) {
                System.out.println("start date saved is after finish date new/update");
                return;
            }else if(availBD.getStartDate().isBefore(dto.getFinishDate()) &&
                    (availBD.getStartDate().isAfter(dto.getStartDate()) ||
                            availBD.getStartDate().isEqual(dto.getStartDate())
                    )){
                throw new AvailabilityConflictException("Conflict with id = x " + availBD.getId());
            }else if((availBD.getStartDate().isBefore(dto.getStartDate())
                    || (availBD.getStartDate().isEqual(dto.getStartDate())) &&
                    (availBD.getFinishDate().isAfter(dto.getStartDate()))
            )){
                throw new AvailabilityConflictException("Conflict with id = y " + availBD.getId());
            }else if(dto.getStartDate().isAfter(availBD.getStartDate())){
                LocalDate supposedFinishDate = dto.getFinishDate().plusDays(-1);
                System.out.println("new finish date is " + supposedFinishDate);
                availBD.setFinishDate(supposedFinishDate);
            }else if(availBD.getStartDate().isEqual(dto.getFinishDate())){
                System.out.println("bd start date = dto finish date");
                validateTime(dto, availBD);
               // validateTime(toDTO(availBD),toEntity(dto));
            }

        if(dto.getId() != null && dto.getId().equals(conflicting.get(0).getId())){
            System.out.println("mesmo registro e passou por outras validações");
            return ;
        }
//        else{
//            throw new AvailabilityConflictException("Conflict with id = e " + conflicting.get(0).getId());
//        }
        //check if it is null and set date finish to date start -1
    }else{
        System.out.println("conflict size bigger than  1");
        String ids = "";
        for (Availability av : conflicting) {
            if(dto.getId() != null && dto.getId().equals(av.getId())){
                continue;
            }
            ids += ", " + av.getId();
        }
        ids = ids.replaceFirst(", ","");
        //return error
        throw new AvailabilityConflictException("Conflict with ids = " + ids);
    }
}
    private static void validateTime(AvailabilityDTO dto, Availability availBD) {
        //initial hour between
        if(( dto.getHourStartTime().after(availBD.getHourStartTime()) ||
                dto.getHourStartTime().equals(availBD.getHourStartTime() )
        ) && ( availBD.getHourFinishTime().after(dto.getHourStartTime()))){
            throw new AvailabilityConflictException("Conflict with id = b " + availBD.getId());
        }//final hour between
        else if( dto.getHourFinishTime().after(availBD.getHourStartTime())
         && ( availBD.getHourFinishTime().after(dto.getHourFinishTime())
                || availBD.getHourFinishTime().equals(dto.getHourFinishTime()) )){
            throw new AvailabilityConflictException("Conflict with id = c " + availBD.getId());
        }//same time
        else if(dto.getHourStartTime().equals(availBD.getHourStartTime()) ||
                availBD.getHourFinishTime().equals(dto.getHourFinishTime())){
            throw new AvailabilityConflictException("Conflict with id = d " + availBD.getId());
        }else {
            System.out.println("No conflict time");
            return;
        }
    }

    public void delete(Long id) {
        findOrThrowAvailabilityById(id);
        availabilityRepository.deleteById(id);
    }

    public void update(Long id, AvailabilityDTO dto) {
        try{
            findOrThrowAvailabilityById(id);
            Availability availability = toEntity(dto);
            validatePeriodAvailability(dto);
            availabilityRepository.save(availability);
        }catch (AvailabilityConflictException e){
            throw new AvailabilityConflictException(e.getMessage());
        }
    }

    private Availability findOrThrowAvailabilityById(Long id) {
        return availabilityRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Availability by id: " + id + "was not found"));
    }

    public List<Availability> findByTreatmentId(long treatmentId, LocalDate dateBook){
        return availabilityRepository.findByTreatmentId(treatmentId, dateBook);
    }

    public List<Availability> findConflicting(long userId, LocalDate startDate, LocalDate finishDate, Time hourStartTime, Time hourFinishTime){
        return availabilityRepository.findConflicting(userId, startDate,finishDate,hourStartTime,hourFinishTime);
    }

}
