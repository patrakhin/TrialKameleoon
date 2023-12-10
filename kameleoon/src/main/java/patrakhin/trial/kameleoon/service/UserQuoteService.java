package patrakhin.trial.kameleoon.service;

import org.springframework.stereotype.Service;
import patrakhin.trial.kameleoon.entity.QuoteData;
import patrakhin.trial.kameleoon.entity.UserData;
import patrakhin.trial.kameleoon.entity.UserQuote;
import patrakhin.trial.kameleoon.repository.UserQuoteRepository;

@Service
public class UserQuoteService {

    private final UserQuoteRepository userQuoteRepository;

    public UserQuoteService(UserQuoteRepository userQuoteRepository){
        this.userQuoteRepository = userQuoteRepository;
    }

    public void addUserQuote(UserData userData, QuoteData quoteData){
        UserQuote userQuote = new UserQuote();
        userQuote.setUserData(userData);
        userQuote.setQuoteData(quoteData);
        userQuoteRepository.save(userQuote);
    }

    public void deleteUserQuote(UserData userData, QuoteData quoteData){
        userQuoteRepository.deleteByUserDataAndQuoteData(userData, quoteData);
    }
}
