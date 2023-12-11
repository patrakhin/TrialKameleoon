package patrakhin.trial.kameleoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patrakhin.trial.kameleoon.entity.UserDataEntity;
import patrakhin.trial.kameleoon.repository.UserDataRepository;
import java.util.Date;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository){
        this.userDataRepository = userDataRepository;
    }

    public UserDataEntity createUser(String name, String email, String password) {
        if (userDataRepository.existsByeMail(email)){
            return null;
        }
        UserDataEntity userDataEntity = new UserDataEntity();
        userDataEntity.setUserName(name);
        userDataEntity.seteMail(email);
        userDataEntity.setPassword(password);
        userDataEntity.setCreationDate(new Date());
        return userDataRepository.save(userDataEntity);
    }
}
