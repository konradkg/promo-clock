package dev.promoclock.user;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private LocalDate signupDate;

    public User() {}

    public User(String login, LocalDate signupDate) {
        this.login = login;
        this.signupDate = signupDate;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public LocalDate getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    @Override
    public String toString() {
        return "Uzytkownik [" + id + "] ma login: " + login;
    }
}
