package com.example.web4_2.service;

import com.example.web4_2.models.User;
import com.example.web4_2.repository.PasswordHasher;
import com.example.web4_2.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(String username, String password) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("неееет такого юзера нет");
        }
        User user = userOptional.get();

        if (!PasswordHasher.verify(password, user.getPassword())) {
            throw new BadCredentialsException("нееет неверный пароль");
        }
        return user;
    }


    public String getPointsAsJson(User user) {

        return user.getPointsAsJson();
    }

}