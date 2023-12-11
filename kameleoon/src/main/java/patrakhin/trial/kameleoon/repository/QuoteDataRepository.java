package patrakhin.trial.kameleoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patrakhin.trial.kameleoon.entity.QuoteDataEntity;

@Repository
public interface QuoteDataRepository extends JpaRepository<QuoteDataEntity, Long> {
}
