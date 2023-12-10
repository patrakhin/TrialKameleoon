package patrakhin.trial.kameleoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patrakhin.trial.kameleoon.entity.QuoteData;

@Repository
public interface QuoteDataRepository extends JpaRepository<QuoteData, Long> {
}
