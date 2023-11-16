package dev.promoclock;

import dev.promoclock.user.User;
import dev.promoclock.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PromoClockApplication {

	private static final Logger log = LoggerFactory.getLogger(PromoClockApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(PromoClockApplication.class, args);

	}

	// Spring to odpali po starcie aplikacji
	@Bean
	public CommandLineRunner initDemoDb(UserRepository userRepository) {
		return (args) -> {
			User admin = new User("admin", LocalDate.now());
			User user1 = new User("konrad", LocalDate.of(2023, 11, 10));
			User user2 = new User("randomUser", LocalDate.of(2023, 5, 5));

			userRepository.save(admin);
			userRepository.save(user1);
			userRepository.save(user2);

			// find all users
			log.info("findAll(), expect 3 users");
			log.info("-------------------------------");
			for (User user : userRepository.findAll()) {
				log.info(user.toString());
			}
			log.info("\n");

		};
	}

}
