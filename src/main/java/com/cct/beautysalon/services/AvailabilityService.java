package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.AvailabilityDTO;
import com.cct.beautysalon.exceptions.AvailabilityConflictException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.exceptions.StartDateAfterFinishDateException;
import com.cct.beautysalon.exceptions.StartTimeAfterFinishTimeException;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.repositories.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ModelMapper mapper;

    /**
     * convert the entity to DTO
     *
     * @param availability
     * @return
     */
    private AvailabilityDTO toDTO(Availability availability) {
        return mapper.map(availability, AvailabilityDTO.class);
    }

    /**
     * convert the DTO to entity
     *
     * @param availabilityDTO
     * @return
     */
    private Availability toEntity(AvailabilityDTO availabilityDTO) {
        return mapper.map(availabilityDTO, Availability.class);
    }

    /**
     * find all availability
     *
     * @return
     */
    public List<AvailabilityDTO> findAllWithTreatments() {
        var availabilities = availabilityRepository.findAllWithTreatments();
        return availabilities.stream().map(this::toDTO).toList();
        //return availabilityRepository.findAllWithTreatments();
    }

    /**
     * find by id
     *
     * @param id
     * @return
     */
    public AvailabilityDTO findAvailabilityById(Long id) {
        return toDTO(findOrThrowAvailabilityById(id));
    }

    /**
     * save availability
     *
     * @param dto
     * @return
     */
    public AvailabilityDTO save(AvailabilityDTO dto) {
        Availability availability = toEntity(dto);
        if (availability.getFinishDate().isBefore(availability.getStartDate())) {
            throw new StartDateAfterFinishDateException();
        } else if (availability.getHourFinishTime().before(availability.getHourStartTime())) {
            throw new StartTimeAfterFinishTimeException();
        }

        try {
            validatePeriodAvailability(dto);
            return toDTO(availabilityRepository.save(availability));
        } catch (AvailabilityConflictException e) {
            throw new AvailabilityConflictException(e.getMessage());
        }
    }

    /**
     * update the availability
     *
     * @param id
     * @param dto
     */
    public void update(Long id, AvailabilityDTO dto) {
        try {
            findOrThrowAvailabilityById(id);
            Availability availability = toEntity(dto);

            if (availability.getFinishDate().isBefore(availability.getStartDate())) {
                throw new StartDateAfterFinishDateException();
            } else if (availability.getHourFinishTime().before(availability.getHourStartTime())) {
                throw new StartTimeAfterFinishTimeException();
            }
            validatePeriodAvailability(dto);
            availabilityRepository.save(availability);
        } catch (AvailabilityConflictException e) {
            throw new AvailabilityConflictException(e.getMessage());
        }
    }

    /**
     * validate period availability
     *
     * @param dto
     */
    private void validatePeriodAvailability(AvailabilityDTO dto) {
        System.out.println("***************** validatePeriodAvailability");
        //check if there is conflicting with another availability for this user
        List<Availability> conflicting = availabilityRepository.findConflicting(dto.getUser().getId(), dto.getStartDate(), dto.getFinishDate(), dto.getHourStartTime(), dto.getHourFinishTime());
        if (conflicting.size() == 0) {
            System.out.println("conflict size is 0");
            return;
        } else if (conflicting.size() == 1) {
            System.out.println("conflict size is 1");
            Availability availBD = conflicting.get(0);
            if (dto.getId() != null && dto.getId().equals(availBD.getId())) {
                //do nothing
            } else if (availBD.getStartDate().isAfter(dto.getFinishDate())) {
                System.out.println("start date saved is after finish date new/update");
                return;
            } else if (availBD.getStartDate().isBefore(dto.getFinishDate()) &&
                    (availBD.getStartDate().isAfter(dto.getStartDate()) ||
                            availBD.getStartDate().isEqual(dto.getStartDate())
                    )) {
                throw new AvailabilityConflictException("Conflict with id = x " + availBD.getId());
            } else if ((availBD.getStartDate().isBefore(dto.getStartDate())
                    || (availBD.getStartDate().isEqual(dto.getStartDate())) &&
                    (availBD.getFinishDate().isAfter(dto.getStartDate()))
            )) {
                throw new AvailabilityConflictException("Conflict with id = y " + availBD.getId());
            } else if (dto.getStartDate().isAfter(availBD.getStartDate())) {
                LocalDate supposedFinishDate = dto.getFinishDate().plusDays(-1);
                System.out.println("new finish date is " + supposedFinishDate);
                availBD.setFinishDate(supposedFinishDate);
            } else if (availBD.getStartDate().isEqual(dto.getFinishDate())) {
                System.out.println("bd start date = dto finish date");
                validateTime(dto, availBD);
            }

            if (dto.getId() != null && dto.getId().equals(conflicting.get(0).getId())) {
                return;
            }
        } else {
            String ids = "";
            for (Availability av : conflicting) {
                if (dto.getId() != null && dto.getId().equals(av.getId())) {
                    continue;
                }
                ids += ", " + av.getId();
            }
            ids = ids.replaceFirst(", ", "");
            //return error
            throw new AvailabilityConflictException("Conflict with ids = " + ids);
        }
    }

    /**
     * validate time
     *
     * @param dto
     * @param availBD
     */
    private static void validateTime(AvailabilityDTO dto, Availability availBD) {
        //initial hour between
        if ((dto.getHourStartTime().after(availBD.getHourStartTime()) ||
                dto.getHourStartTime().equals(availBD.getHourStartTime())
        ) && (availBD.getHourFinishTime().after(dto.getHourStartTime()))) {
            throw new AvailabilityConflictException("Conflict with id = b " + availBD.getId());
        }//final hour between
        else if (dto.getHourFinishTime().after(availBD.getHourStartTime())
                && (availBD.getHourFinishTime().after(dto.getHourFinishTime())
                || availBD.getHourFinishTime().equals(dto.getHourFinishTime()))) {
            throw new AvailabilityConflictException("Conflict with id = c " + availBD.getId());
        }//same time
        else if (dto.getHourStartTime().equals(availBD.getHourStartTime()) ||
                availBD.getHourFinishTime().equals(dto.getHourFinishTime())) {
            throw new AvailabilityConflictException("Conflict with id = d " + availBD.getId());
        } else {
            System.out.println("No conflict time");
            return;
        }
    }

    /**
     * delete
     *
     * @param id
     */
    public void delete(Long id) {
        findOrThrowAvailabilityById(id);
        availabilityRepository.deleteById(id);
    }


    /**
     * find or throw availability
     *
     * @param id
     * @return
     */
    private Availability findOrThrowAvailabilityById(Long id) {
        return availabilityRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Availability by id: " + id + "was not found"));
    }

    /**
     * find treatment by id
     *
     * @param treatmentId
     * @param dateBook
     * @return
     */
    public List<Availability> findByTreatmentId(long treatmentId, LocalDate dateBook) {
        return availabilityRepository.findByTreatmentId(treatmentId, dateBook);
    }

    /**
     * find conflicting Availability
     *
     * @param userId
     * @param startDate
     * @param finishDate
     * @param hourStartTime
     * @param hourFinishTime
     * @return
     */
    public List<Availability> findConflicting(long userId, LocalDate startDate, LocalDate finishDate, Time hourStartTime, Time hourFinishTime) {
        return availabilityRepository.findConflicting(userId, startDate, finishDate, hourStartTime, hourFinishTime);
    }

}
