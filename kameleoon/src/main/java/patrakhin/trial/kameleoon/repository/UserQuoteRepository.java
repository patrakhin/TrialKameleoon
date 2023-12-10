package patrakhin.trial.kameleoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patrakhin.trial.kameleoon.entity.QuoteData;
import patrakhin.trial.kameleoon.entity.UserData;
import patrakhin.trial.kameleoon.entity.UserQuote;

@Repository
public interface UserQuoteRepository extends JpaRepository<UserQuote, Long> {
    void deleteByUserDataAndQuoteData(UserData userData, QuoteData quoteData);
}
