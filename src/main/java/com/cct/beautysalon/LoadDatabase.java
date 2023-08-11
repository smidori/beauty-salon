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

    public LoadDatabase(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   TreatmentTypeRepository treatmentTypeRepository,
                                   TreatmentRepository treatmentRepository,
                                   AvailabilityRepository avaialabilityRepository,
                                   ProductRepository productRepository
            , BookRepository bookRepository
    ) {
        return args -> {
            boolean createRegister = true;

            List<User> users = userRepository.findAll();
            if(users.size() > 0){
                createRegister = false;
            }

            if (createRegister) {
                LOG.info("Add user to database");
                String password1 = "123";
                String encoderPWD = passwordEncoder.encode(password1);
                userRepository.save(new User("admin", "admin", "admin", encoderPWD, "smstestcct@gmail.com", Role.ADMIN, "female", "+353(83)1231211"));
                userRepository.save(new User("Jamie", "MacQuillan", "jam", encoderPWD, "sms.testcct@gmail.com", Role.WORKER, "male", "+353(82)1231212"));
                userRepository.save(new User("Zoe", "Broderick", "zoe", encoderPWD, "sms.test.cct@gmail.com", Role.WORKER, "female", "+353(85)1231213"));
                userRepository.save(new User("Patrick", "Sullivan", "pat", encoderPWD, "smstest.cct@gmail.com", Role.WORKER, "male", "+353(87)1231214"));
                userRepository.save(new User("Penelope", "Morrison", "pen", encoderPWD, "sm.stestcct@gmail.com", Role.WORKER, "female", "+353(88)1231215"));
                userRepository.save(new User("Catarine", "Morrison", "cat", encoderPWD, "smste.st.cct@gmail.com", Role.CLIENT, "female", "+353(88)1231112"));
                userRepository.save(new User("John", "Willis", "john", encoderPWD, "s.mstestcct@gmail.com", Role.CLIENT, "male", "+353(88)1231115"));

                User userWorker2 = userRepository.findById(2L).get();
                User userWorker3 = userRepository.findById(3L).get();
                User userWorker4 = userRepository.findById(4L).get();
                User userWorker5 = userRepository.findById(5L).get();

                User clientUser6 = userRepository.findById(6L).get();
                User clientUser7 = userRepository.findById(7L).get();

                LOG.info("Add treatments type to database");
                treatmentTypeRepository.save(new TreatmentType("Kids treats"));
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
                treatmentRepository.save(new Treatment("Kids haircut - 2y", "Until 2 years old", nails, 30, 60));
                treatmentRepository.save(new Treatment("Kids haircut - 3y", "older than 2 years old", nails, 25, 30));
                treatmentRepository.save(new Treatment("Classic Manicure/Pedicure", "Nail art (+5) shellac increase more (+15)", ladiesTreats, 20, 40));
                treatmentRepository.save(new Treatment("Luxury Manicure/Pedicure", "Nail art (+5) shellac increase more (+15)", ladiesTreats, 25, 60));
                treatmentRepository.save(new Treatment("Short haircut", "shampoo-blow-dry", ladiesTreats, 50, 45));
                treatmentRepository.save(new Treatment("Long haircut", "shampoo-blow-dry", ladiesTreats, 55, 60));
                treatmentRepository.save(new Treatment("Haircut", "shampoo-blow-dry", maleTreats, 25, 30));
                treatmentRepository.save(new Treatment("Beard", "beard service", maleTreats, 15, 20));
                treatmentRepository.save(new Treatment("Oil massage", "Hot Oil Back & Neck Massage", massage, 50, 60));
                treatmentRepository.save(new Treatment("Back & shoulder massage", "To relieve stress and muscles aches on the back, shoulder and neck", massage, 30, 30));
                treatmentRepository.save(new Treatment("Full leg", "waxing full leg", waxing, 35, 90));
                treatmentRepository.save(new Treatment("Half leg", "waxing half leg (Upper or Lower)", waxing, 25, 60));

//            Treatment treatment1 = treatmentRepository.findById(1L).get();
//            Treatment treatment2 = treatmentRepository.findById(2L).get();
//            Treatment treatment3 = treatmentRepository.findById(3L).get();
//            Treatment treatment4 = treatmentRepository.findById(4L).get();
//            Treatment treatment5 = treatmentRepository.findById(5L).get();
//            Treatment treatment6 = treatmentRepository.findById(6L).get();
//
//
//            LOG.info("Add availability to database");
//            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//            LocalDate dateStart = LocalDate.parse("28/06/2023", dateFormat);
//            LocalDate dateStart2 = LocalDate.parse("23/07/2023", dateFormat);
//            LocalDate dateStart3 = LocalDate.parse("28/07/2023", dateFormat);
//
//            //List<Treatment> w1Treatments = new ArrayList<>();
//            Set<Treatment> w1Treatments = new HashSet<>();
//            w1Treatments.add(treatment5);
//            w1Treatments.add(treatment6);
//
//            //List<Treatment> w2Treatments = new ArrayList<>();
//            Set<Treatment> w2Treatments = new HashSet<>();
//            w2Treatments.add(treatment5);
//            //w2Treatments.add(treatment6);
//
//            Set<Treatment> w3Treatments = new HashSet<>();
//            w3Treatments.add(treatment1);
//            w3Treatments.add(treatment2);
//            w3Treatments.add(treatment6);
//
//            Set<Treatment> w4Treatments = new HashSet<>();
//            w4Treatments.add(treatment1);
//            w4Treatments.add(treatment2);
//            w4Treatments.add(treatment3);
//            w4Treatments.add(treatment4);
//
//            avaialabilityRepository.save(new Availability(w1Treatments,userWorker2,dateStart,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
//
//            avaialabilityRepository.save(new Availability(w2Treatments,userWorker3, dateStart,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
//
//            avaialabilityRepository.save(new Availability(w3Treatments,userWorker4,dateStart2,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime())));
//
//            avaialabilityRepository.save(new Availability(w4Treatments,userWorker5, dateStart3,null,
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
//                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
//
////            avaialabilityRepository.save(new Availability(w1Treatments,userWorker2,true,true,true,true,true,true,false,dateStart,null,
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));
////
////            avaialabilityRepository.save(new Availability(w2Treatments,userWorker3,true,false,true,false,true,true,false,dateStart,null,
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("12:00").getTime()) ,
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("13:00").getTime()),
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("09:00").getTime()),
////                    new java.sql.Time(new SimpleDateFormat("HH:mm").parse("18:00").getTime())));


                //products
                productRepository.save(new Product("Shampoo", "Oil hair", 15, 2));
                productRepository.save(new Product("Conditioner", "Oil hair", 20, 1));
                productRepository.save(new Product("Shampoo Curly hair", "Oil hair", 15, 3));
                productRepository.save(new Product("Conditioner Curly hair", "Oil hair", 20, 3));
                productRepository.save(new Product("Body lotion", "Oil massage", 25, 1));
                productRepository.save(new Product("Palmer cocoa butter", "Cruelty free", 10, 1));
                productRepository.save(new Product("Nail colour N01", "", 8.50, 2));
                productRepository.save(new Product("Nail colour N01", "Purple", 8.50, 2));
                productRepository.save(new Product("Nail colour N02", "Gray", 8.50, 2));
                productRepository.save(new Product("Nail colour N03", "Yellow", 8.50, 2));
                productRepository.save(new Product("Nail colour N04", "Green", 8.50, 2));
                productRepository.save(new Product("Nail colour N05", "White", 8.50, 2));
                productRepository.save(new Product("Nail colour N06", "Black", 8.50, 2));
                productRepository.save(new Product("Nail colour N07", "Pink", 8.50, 2));
                productRepository.save(new Product("Nail colour N08", "Nude", 8.50, 2));


//            //books
//            bookRepository.save(new Book(treatment1,
//                    LocalDate.parse("26/07/2023", dateFormat),
//                    LocalTime.of(9, 0),
//                    LocalTime.of(9, 30),
//                    clientUser6,
//                    userWorker4,
//                    LocalDateTime.now(),
//                    BookStatus.COMPLETED,
//                    "description 1 from client lorem ipsum dolor sit amet"
//            ));
//
//
//            bookRepository.save(new Book(treatment5,
//                    LocalDate.parse("26/07/2023", dateFormat),
//                    LocalTime.of(10, 30),
//                    LocalTime.of(11, 15),
//                    clientUser6,
//                    userWorker3,
//                    LocalDateTime.now(),
//                    BookStatus.COMPLETED,
//                    "description 1 from client lorem ipsum dolor sit amet"
//            ));
//
//
//            bookRepository.save(new Book(treatment1,
//                    LocalDate.parse("28/07/2023", dateFormat),
//                    LocalTime.of(9, 0),
//                    LocalTime.of(9, 30),
//                    clientUser6,
//                    userWorker4,
//                    LocalDateTime.now(),
//                    BookStatus.BOOKED,
//                    "description 2 from client lorem ipsum dolor sit amet"
//            ));
            }

        };
    }
}
