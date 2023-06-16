package com.cct.beautysalon;

import com.cct.beautysalon.enums.UserType;
import com.cct.beautysalon.models.User;
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
    CommandLineRunner initDatabase(UserRepository userRepository){
        return args -> {
            LOG.info("Add user to database");

            userRepository.save(new User("Silvia", "Shimabuko", "smidori", "test","smidori@gmail.com", UserType.CLIENT));
            userRepository.save(new User("Test", "Dev", "test", "test","test@gmail.com", UserType.WORKER));
        };
    }
}
