package dev.promoclock.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.signupDate > :date")
    List<User> findUsersRegistredAfterDate(@Param("date") LocalDate date);

}
