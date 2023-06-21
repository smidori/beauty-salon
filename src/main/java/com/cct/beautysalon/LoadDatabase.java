package com.cct.beautysalon;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.TreatmentType;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.TreatmentRepository;
import com.cct.beautysalon.repositories.TreatmentTypeRepository;
import com.cct.beautysalon.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   TreatmentTypeRepository treatmentTypeRepository,
                                   TreatmentRepository treatmentRepository){
        return args -> {
            LOG.info("Add user to database");

            //userRepository.save(new User("Silvia", "Shimabuko", "smidori", "test","smidori@gmail.com", Role.CLIENT));
            //userRepository.save(new User("Test", "Dev", "test", "test","test@gmail.com", Role.WORKER));
            userRepository.save(new User("aaa", "bbb", "admin", "$2a$10$SFpBn1tw/0buJA5OhwJvPeltclKhnom72Tu.WC7dLzpKu75m3OjOu","smidori@gmail.com", Role.ADMIN));
            userRepository.save(new User("ccc", "ddd", "client", "$2a$10$cc2sW4BvfJMHsCATGktQ2OC9kCYt2.ckTr1AOg4gheza28Su0yBge","smidori@gmail.com", Role.CLIENT));


            LOG.info("Add treatments type to database");
            treatmentTypeRepository.save(new TreatmentType("Nails"));
            treatmentTypeRepository.save(new TreatmentType("Haircuts"));
            treatmentTypeRepository.save(new TreatmentType("Barber"));
            treatmentTypeRepository.save(new TreatmentType("Massage"));
            treatmentTypeRepository.save(new TreatmentType("Eyelash"));
            TreatmentType nails = treatmentTypeRepository.findById(1L).get();

            LOG.info("Add treatments to database");

            treatmentRepository.save(new Treatment("Manicure","desc x", nails, 22));
            treatmentRepository.save(new Treatment("BIAB nails with shellac","desc x", nails, 42));
            treatmentRepository.save(new Treatment("Shellac removal & mini manicure","desc x", nails, 1));

        };
    }
}
