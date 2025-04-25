package com.example.web4_2.restapi;

import com.example.web4_2.repository.PasswordHasher;
import com.example.web4_2.models.User;
import com.example.web4_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/pim")
    public ResponseEntity<?> getPim(@RequestHeader(name="Accept-Language", defaultValue = "rus") Locale lang) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message",
                        messageSource.getMessage("pim", null, lang)
                ));
    }

    @GetMapping("/login")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/login")
    ResponseEntity<?> addUser(@RequestBody User user, @RequestHeader(name="Accept-Language", defaultValue = "rus") Locale lang) {

        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            messageSource.getMessage("error.no_such_user", null, lang)
                    ));
        }
        User db_user = userRepository.findByUsername(user.getUsername()).get();
        System.out.println("я кое что нашел");
        System.out.println(db_user.getId() + " " + db_user.getUsername() + " " + db_user.getPassword());
        System.out.println(user.getId() + " " + user.getUsername() + " " + user.getPassword());

        user.setId(db_user.getId());
        if (!PasswordHasher.verify(user.getPassword(), db_user.getPassword())) {
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            messageSource.getMessage("error.wrong_password", null, lang)
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user, @RequestHeader(name="Accept-Language", defaultValue = "rus") Locale lang) {

        System.out.println("счас мы будем регаться");
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message",
                            messageSource.getMessage("error.username_already_exists", null, lang)));
        }

        user.setPassword(PasswordHasher.hash(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("userId", user.getId()));
    }
}
