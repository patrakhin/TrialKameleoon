package patrakhin.trial.kameleoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patrakhin.trial.kameleoon.entity.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    boolean existsByEMail(String email);
}
