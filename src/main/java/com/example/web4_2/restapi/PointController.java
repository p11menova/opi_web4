package com.example.web4_2.restapi;

import com.example.web4_2.service.PointService;
import com.example.web4_2.service.UserService;
import com.example.web4_2.service.areaCheck.AreaChecker;
import com.example.web4_2.repository.PasswordHasher;
import com.example.web4_2.models.User;
import com.example.web4_2.models.UserDTO;
import com.example.web4_2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PointController {
    private final UserRepository userRepository;
    private final PointService pointService;
    private final UserService userService;

    public PointController(UserRepository userRepository, PointService pointService, UserService userService) {
        this.userRepository = userRepository;
        this.pointService = pointService;
        this.userService = userService;
    }

    @GetMapping("/points")
    public ResponseEntity<?> getPoints(@RequestParam String username, @RequestParam String password) {

        try {
            User user = userService.authenticateUser(username, password);
            System.out.println("да такой юзер есть");
            String pointsAsJson = userService.getPointsAsJson(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pointsAsJson);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearPoints(@RequestBody UserDTO userDTO) {
        System.out.println("я поймал делит реквест");
        System.out.println(userDTO.getUsername());
        try {
            User user = userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());
            user.getPoints().clear();
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String username, @RequestParam String password, @RequestParam float x, @RequestParam float y, @RequestParam float r) {

        System.out.println("я счас буду проверять ПОПАЛА ли точка");
        System.out.println(x + " " + y + " " + r);
        try {

            User user = userService.authenticateUser(username, password);
            String pointStringFormat = pointService.processPoint(x, y, r, user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pointStringFormat);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }


    }
    // опционально, для тестирования или типа выводить статистику по кликам на фронте
    @GetMapping("/mbean-stats")
    public ResponseEntity<String> getMBeanStats() {
        String stats = String.format("всего точек: %d, промахов: %d, процент попадания: %.2f%%",
                pointService.getTotalPointsCount(),
                pointService.getMissedPointsCount(),
                pointService.getHitRatioPercentage());
        return ResponseEntity.ok(stats);
    }
}


