package patrakhin.trial.kameleoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patrakhin.trial.kameleoon.entity.UserData;
import patrakhin.trial.kameleoon.repository.UserDataRepository;
import java.util.Date;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository){
        this.userDataRepository = userDataRepository;
    }

    public UserData createUser(String name, String email, String password) {
        if (userDataRepository.existsByEMail(email)){
            return null;
        }
        UserData userData = new UserData();
        userData.setUserName(name);
        userData.seteMail(email);
        userData.setPassword(password);
        userData.setCreationDate(new Date());
        return userDataRepository.save(userData);
    }
}
