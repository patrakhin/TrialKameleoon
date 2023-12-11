package patrakhin.trial.kameleoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patrakhin.trial.kameleoon.entity.UserDataEntity;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, Long> {
    boolean existsByeMail(String email);
}
