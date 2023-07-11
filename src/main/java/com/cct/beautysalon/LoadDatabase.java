package com.cct.beautysalon;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.TreatmentType;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.AvailabilityRepository;
import com.cct.beautysalon.repositories.TreatmentRepository;
import com.cct.beautysalon.repositories.TreatmentTypeRepository;
import com.cct.beautysalon.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Configuration
public class LoadDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   TreatmentTypeRepository treatmentTypeRepository,
                                   TreatmentRepository treatmentRepository,
                                   AvailabilityRepository avaialabilityRepository){
        return args -> {
            LOG.info("Add user to database");

            //userRepository.save(new User("Silvia", "Shimabuko", "smidori", "test","smidori@gmail.com", Role.CLIENT));
            //userRepository.save(new User("Test", "Dev", "test", "test","test@gmail.com", Role.WORKER));
            userRepository.save(new User("aaa", "bbb", "admin", "$2a$10$SFpBn1tw/0buJA5OhwJvPeltclKhnom72Tu.WC7dLzpKu75m3OjOu","smidori@gmail.com", Role.ADMIN,"female"));
            userRepository.save(new User("worker", "first", "worker", "$2a$10$cc2sW4BvfJMHsCATGktQ2OC9kCYt2.ckTr1AOg4gheza28Su0yBge","smidori@gmail.com", Role.WORKER,"female"));
            userRepository.save(new User("workerII", "two", "worker2", "$10$Z6ll4m2lG2QH1WkbPNVwMOdPvMLxXC6cf0MNgChVK/XNaHth41Vwa","smidori@gmail.com", Role.WORKER,"male"));
            User userWorker1 = userRepository.findById(2L).get();
            User userWorker2 = userRepository.findById(3L).get();

            LOG.info("Add treatments type to database");
            treatmentTypeRepository.save(new TreatmentType("Nails"));
            treatmentTypeRepository.save(new TreatmentType("Ladies treats"));
            treatmentTypeRepository.save(new TreatmentType("Gents treats"));
            treatmentTypeRepository.save(new TreatmentType("Massage"));
            treatmentTypeRepository.save(new TreatmentType("Waxing"));
            TreatmentType nails = treatmentTypeRepository.findById(1L).get();
            TreatmentType ladiesTreats = treatmentTypeRepository.findById(2L).get();
            TreatmentType maleTreats = treatmentTypeRepository.findById(3L).get();
            TreatmentType massage = treatmentTypeRepository.findById(4L).get();
            TreatmentType waxing = treatmentTypeRepository.findById(5L).get();

            LOG.info("Add treatments to database");
            treatmentRepository.save(new Treatment("Manicure","regular", nails, 15,30));
            treatmentRepository.save(new Treatment("Manicure","BIAB nails with shellac", nails, 22, 30));
            treatmentRepository.save(new Treatment("Pedicure","regular", nails, 20, 60));
            treatmentRepository.save(new Treatment("Pedicure","BIAB nails with shellac", nails, 25, 60));
            treatmentRepository.save(new Treatment("senior haircut","ladies haircut and blowdry", ladiesTreats, 50, 45));
            treatmentRepository.save(new Treatment("expert haircut","ladies haircut and blowdry", ladiesTreats, 55, 60));
            treatmentRepository.save(new Treatment("haircut","gents haircut", maleTreats, 25, 30));
            treatmentRepository.save(new Treatment("beard","beard service", maleTreats, 15, 30));
            treatmentRepository.save(new Treatment("Hot Oil Back & Neck Massage","30 min", massage, 50, 60));
            treatmentRepository.save(new Treatment("Aromatic Face & Scalp Massage","30 min", massage, 50, 60));
            treatmentRepository.save(new Treatment("full leg","waxing full leg", waxing, 35, 60));
            treatmentRepository.save(new Treatment("half leg","waxing half leg", waxing, 25, 60));

            Treatment treatment5 = treatmentRepository.findById(5L).get();
            Treatment treatment6 = treatmentRepository.findById(6L).get();

            LOG.info("Add availability to database");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateStart = (Date) dateFormat.parse("23/5/2023");
            //List<Treatment> w1Treatments = new ArrayList<>();
            Set<Treatment> w1Treatments = new HashSet<>();
            w1Treatments.add(treatment5);
            w1Treatments.add(treatment6);

            //List<Treatment> w2Treatments = new ArrayList<>();
            Set<Treatment> w2Treatments = new HashSet<>();
            w2Treatments.add(treatment5);
            //w2Treatments.add(treatment6);

            avaialabilityRepository.save(new Availability(w1Treatments,userWorker1,true,true,true,true,true,true,false,dateStart,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));

            avaialabilityRepository.save(new Availability(w2Treatments,userWorker2,true,false,true,false,true,true,false,dateStart,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
        };
    }
}
