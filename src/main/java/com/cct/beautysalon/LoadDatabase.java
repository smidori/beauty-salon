package com.cct.beautysalon;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.*;
import com.cct.beautysalon.repositories.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Configuration
public class LoadDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);
    private final PasswordEncoder passwordEncoder;

    public LoadDatabase(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   TreatmentTypeRepository treatmentTypeRepository,
                                   TreatmentRepository treatmentRepository,
                                   AvailabilityRepository avaialabilityRepository,
                                   ProductRepository productRepository,
                                   BookRepository bookRepository){
        return args -> {
            LOG.info("Add user to database");

            //userRepository.save(new User("Silvia", "Shimabuko", "smidori", "test","smidori@gmail.com", Role.CLIENT));
            //userRepository.save(new User("Test", "Dev", "test", "test","test@gmail.com", Role.WORKER));
            String password1 = "123";
            String encoderPWD = passwordEncoder.encode(password1);
            //userRepository.save(new User("aaa", "bbb", "admin", "$2a$10$SFpBn1tw/0buJA5OhwJvPeltclKhnom72Tu.WC7dLzpKu75m3OjOu","smidori@gmail.com", Role.ADMIN,"female"));
            userRepository.save(new User("admin", "admin", "admin", encoderPWD,"smidori@gmail.com", Role.ADMIN,"female","+353(83)1231211"));
            userRepository.save(new User("Jamie", "MacQuillan", "jam", encoderPWD,"smidori@gmail.com", Role.WORKER,"male","+353(82)1231212"));
            userRepository.save(new User("Zoe", "Broderick", "zoe", encoderPWD,"smidori@gmail.com", Role.WORKER,"female","+353(85)1231213"));
            userRepository.save(new User("Patrick", "Sullivan", "pat", encoderPWD,"smidori@gmail.com", Role.WORKER,"male","+353(87)1231214"));
            userRepository.save(new User("Penelope", "Morrison", "pen", encoderPWD,"smidori@gmail.com", Role.WORKER,"female","+353(88)1231215"));
            userRepository.save(new User("Catarine", "Morrison", "cat", encoderPWD,"smidori@gmail.com", Role.CLIENT,"female","+353(88)1231112"));
            userRepository.save(new User("John", "Willis", "john", encoderPWD,"smidori@gmail.com", Role.CLIENT,"male","+353(88)1231115"));

            User userWorker2 = userRepository.findById(2L).get();
            User userWorker3 = userRepository.findById(3L).get();
            User userWorker4 = userRepository.findById(4L).get();
            User userWorker5 = userRepository.findById(5L).get();

            User clientUser6 = userRepository.findById(6L).get();
            User clientUser7 = userRepository.findById(7L).get();

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
            treatmentRepository.save(new Treatment("Manicure Shellac","BIAB nails with shellac", nails, 22, 30));
            treatmentRepository.save(new Treatment("Pedicure","regular", nails, 20, 60));
            treatmentRepository.save(new Treatment("Pedicure Shellac","BIAB nails with shellac", nails, 25, 60));
            treatmentRepository.save(new Treatment("senior haircut","ladies haircut and blowdry", ladiesTreats, 50, 45));
            treatmentRepository.save(new Treatment("expert haircut","ladies haircut and blowdry", ladiesTreats, 55, 60));
            treatmentRepository.save(new Treatment("haircut","gents haircut", maleTreats, 25, 30));
            treatmentRepository.save(new Treatment("beard","beard service", maleTreats, 15, 30));
            treatmentRepository.save(new Treatment("Hot Oil Back & Neck Massage","30 min", massage, 50, 60));
            treatmentRepository.save(new Treatment("Aromatic Face & Scalp Massage","30 min", massage, 50, 60));
            treatmentRepository.save(new Treatment("full leg","waxing full leg", waxing, 35, 60));
            treatmentRepository.save(new Treatment("half leg","waxing half leg", waxing, 25, 60));

            Treatment treatment1 = treatmentRepository.findById(1L).get();
            Treatment treatment2 = treatmentRepository.findById(2L).get();
            Treatment treatment3 = treatmentRepository.findById(3L).get();
            Treatment treatment4 = treatmentRepository.findById(4L).get();
            Treatment treatment5 = treatmentRepository.findById(5L).get();
            Treatment treatment6 = treatmentRepository.findById(6L).get();

            LOG.info("Add availability to database");
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dateStart = LocalDate.parse("28/06/2023", dateFormat);
            LocalDate dateStart2 = LocalDate.parse("23/07/2023", dateFormat);
            LocalDate dateStart3 = LocalDate.parse("28/07/2023", dateFormat);

            //List<Treatment> w1Treatments = new ArrayList<>();
            Set<Treatment> w1Treatments = new HashSet<>();
            w1Treatments.add(treatment5);
            w1Treatments.add(treatment6);

            //List<Treatment> w2Treatments = new ArrayList<>();
            Set<Treatment> w2Treatments = new HashSet<>();
            w2Treatments.add(treatment5);
            //w2Treatments.add(treatment6);

            Set<Treatment> w3Treatments = new HashSet<>();
            w3Treatments.add(treatment1);
            w3Treatments.add(treatment2);
            w3Treatments.add(treatment6);

            Set<Treatment> w4Treatments = new HashSet<>();
            w4Treatments.add(treatment1);
            w4Treatments.add(treatment2);
            w4Treatments.add(treatment3);
            w4Treatments.add(treatment4);

            avaialabilityRepository.save(new Availability(w1Treatments,userWorker2,dateStart,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));

            avaialabilityRepository.save(new Availability(w2Treatments,userWorker3, dateStart,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));

            avaialabilityRepository.save(new Availability(w3Treatments,userWorker4,dateStart2,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime())));

            avaialabilityRepository.save(new Availability(w4Treatments,userWorker5, dateStart3,null,
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));

//            avaialabilityRepository.save(new Availability(w1Treatments,userWorker2,true,true,true,true,true,true,false,dateStart,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
//
//            avaialabilityRepository.save(new Availability(w2Treatments,userWorker3,true,false,true,false,true,true,false,dateStart,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));


            //products
            productRepository.save(new Product("Shampoo", "Oil hair",15));
            productRepository.save(new Product("Conditioner", "Oil hair",20));
            productRepository.save(new Product("Shampoo Curly hair", "Oil hair",15));
            productRepository.save(new Product("Conditioner Curly hair", "Oil hair",20));
            productRepository.save(new Product("Body lotion", "Oil massage",45));

            //books
            bookRepository.save(new Book(treatment1,
                    LocalDate.parse("23/07/2023", dateFormat),
                    LocalTime.of(9, 0),
                    LocalTime.of(9, 30),
                    clientUser6,
                    userWorker4,
                    LocalDateTime.now(),
                    BookStatus.IN_SERVICE,
                    "description 1 from client lorem ipsum dolor sit amet"
            ));

            bookRepository.save(new Book(treatment1,
                    LocalDate.parse("28/07/2023", dateFormat),
                    LocalTime.of(9, 0),
                    LocalTime.of(9, 30),
                    clientUser6,
                    userWorker4,
                    LocalDateTime.now(),
                    BookStatus.BOOKED,
                    "description 2 from client lorem ipsum dolor sit amet"
            ));


        };
    }
}
