//package com.example.bpmsenterprise.layers.services;
//
//import com.example.bpmsenterprise.entity.User;
//import com.example.bpmsenterprise.repos.UserRepo;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserService implements UserDetailsService{
//    private final UserRepo userRepo;
//
//    public Optional<User> findByUserName(String username){
//        return userRepo.findByUsername(username);
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUserName(username).orElseThrow(()-> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getName(),
//                user.getPassword(),
//                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
//        );
//    }
//
//}
