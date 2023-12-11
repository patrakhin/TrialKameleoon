package patrakhin.trial.kameleoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patrakhin.trial.kameleoon.entity.UserDataEntity;
import patrakhin.trial.kameleoon.service.UserDataService;

@RestController
@RequestMapping("/api/users")
public class UserDataController {
    private final UserDataService userDataService;

    @Autowired
    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDataEntity> registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        UserDataEntity newUser = userDataService.createUser(name, email, password);

        if (newUser != null) {
            return ResponseEntity.ok(newUser);
        } else {
            // Обработка ошибки (возвращение HTTP-статуса конфликта)
            return ResponseEntity.status(401).build();
        }
    }
}
