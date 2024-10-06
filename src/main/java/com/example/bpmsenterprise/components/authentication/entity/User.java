package com.example.bpmsenterprise.components.authentication.entity;

import com.example.bpmsenterprise.components.authentication.token.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_day")
    private String birth_day;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public static class UserBuilder {
        private Integer id;
        private String firstname;
        private String lastname;
        private String birth_day;
        private String password;
        private String email;
        private String phone;
        private String surname;
        private Role role;
        private List<Token> tokens;

        UserBuilder() {
        }

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserBuilder birth_day(String birth_day) {
            this.birth_day = birth_day;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder tokens(List<Token> tokens) {
            this.tokens = tokens;
            return this;
        }

        public User build() {
            return new User(this.id, this.firstname, this.lastname, this.birth_day, this.password, this.email, this.phone, this.surname, this.role, this.tokens);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", firstname=" + this.firstname + ", lastname=" + this.lastname + ", birth_day=" + this.birth_day + ", password=" + this.password + ", email=" + this.email + ", phone=" + this.phone + ", surname=" + this.surname + ", role=" + this.role + ", tokens=" + this.tokens + ")";
        }
    }
}
