package com.example.web4_2.restapi;

import com.example.web4_2.areaCheck.AreaChecker;
import com.example.web4_2.repository.PasswordHasher;
import com.example.web4_2.models.altPoint;
import com.example.web4_2.models.User;
import com.example.web4_2.models.UserDTO;
import com.example.web4_2.repository.altPointRepository;
import com.example.web4_2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class altPointController {
    private final UserRepository userRepository;
    private altPointRepository pointRepository;

    public altPointController(altPointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/points")
    public ResponseEntity<?> getaltPoints(@RequestParam String username, @RequestParam String password) {
        System.out.println("я счас буду отдавать точки");

        if (userRepository.findByUsername(username).isEmpty())
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "такого юзера нет"
                    ));
        User user = userRepository.findByUsername(username).get();

        if (!PasswordHasher.verify(password, user.getPassword())) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "эмм пароли не совпадают"
                    ));
        }
        System.out.println("да такой юзер есть");

        String pointsAsJson = user.getaltPointsAsJson();
        System.out.println(pointsAsJson);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pointsAsJson);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearaltPoints(@RequestBody UserDTO userDTO) {
        System.out.println("я поймал делит реквест");
        System.out.println(userDTO.getUsername());

        if (userRepository.findByUsername(userDTO.getUsername()).isEmpty())
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body("такого юзера нет");
        User user = userRepository.findByUsername(userDTO.getUsername()).get();
        if (!PasswordHasher.verify(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body("эмм пароли не совпадают");
        }

        user.getaltPoints().clear();
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String username, @RequestParam String password, @RequestParam float x, @RequestParam float y, @RequestParam float r) {
        long executionStartTime = System.currentTimeMillis();
        System.out.println("я счас буду проверять ПОПАЛА ли точка");
        System.out.println(x + " " + y + " " + r);

        if (userRepository.findByUsername(username).isEmpty())
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "такого юзера нет"
                    ));
        User user = userRepository.findByUsername(username).get();
        if (!PasswordHasher.verify(password, user.getPassword())) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message", "эмм пароли не совпадают"
                    ));
        }


        altPoint point = new altPoint();
        point.setX(x);
        point.setY(y);
        point.setRadius(r);
        point.setResult(checkArea(x, y, r));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);

        point.setCur_time(formattedTime);
        point.setScript_time(System.currentTimeMillis() - executionStartTime);

        point.setUser(user);


        user.getaltPoints().add(point);
        System.out.println(point.toString());
        System.out.println("дада счас отдам новую точку");

        pointRepository.save(point);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(point.toString());
    }

    public boolean checkArea(float x, float y, float r) {
        return AreaChecker.check(x, y, r);
    }

}


