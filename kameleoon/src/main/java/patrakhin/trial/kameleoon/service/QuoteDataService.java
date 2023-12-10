package patrakhin.trial.kameleoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import patrakhin.trial.kameleoon.entity.QuoteData;
import patrakhin.trial.kameleoon.entity.UserData;
import patrakhin.trial.kameleoon.repository.QuoteDataRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuoteDataService {

    private final QuoteDataRepository quoteDataRepository;
    private final UserQuoteService userQuoteService;
    private final Random random;

    @Autowired
    public QuoteDataService(QuoteDataRepository quoteDataRepository, UserQuoteService userQuoteService){
        this.quoteDataRepository = quoteDataRepository;
        this.userQuoteService = userQuoteService;
        this.random = new Random();
    }

    public QuoteData createQuoteData(String content, UserData userData){
        QuoteData quoteData = new QuoteData();
        quoteData.setContent(content);
        quoteData.setCreationDate(new Date());
        QuoteData savedQuote = quoteDataRepository.save(quoteData);
        userQuoteService.addUserQuote(userData, quoteData);
        return savedQuote;
    }

    public Optional<QuoteData> getQuoteById(Long id) {
        return quoteDataRepository.findById(id);
    }

    public List<QuoteData> getAllQuotes() {
        return quoteDataRepository.findAll();
    }

    public QuoteData updateQuote(Long id, String newContent) {
        Optional<QuoteData> optionalQuote = quoteDataRepository.findById(id);
        if (optionalQuote.isPresent()) {
            QuoteData quote = optionalQuote.get();
            quote.setContent(newContent);
            quote.setUpdateDate(new Date());
            return quoteDataRepository.save(quote);
        } else {
            // Обработка случая, когда цитата с заданным id не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteQuote(UserData user, Long quoteId) {
        Optional<QuoteData> optionalQuote = quoteDataRepository.findById(quoteId);
        if (optionalQuote.isEmpty()) {
            // Цитата не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        QuoteData quote = optionalQuote.get();
        if (quote.getUserQuotes() == null || quote.getUserQuotes().stream()
                .noneMatch(userQuote -> userQuote.getUserData().equals(user))) {
            // Непринадлежит пользователю
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        userQuoteService.deleteUserQuote(user,quote);
        quoteDataRepository.deleteById(quoteId);
    }

    public QuoteData getRandomQuote() {
        List<QuoteData> allQuotes = quoteDataRepository.findAll();
        if (!allQuotes.isEmpty()) {
            int randomIndex = random.nextInt(allQuotes.size());
            return allQuotes.get(randomIndex);
        } else {
            // Обработка случая, когда нет доступных цитат
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void voteForQuote(long quoteId, boolean isUpVote) {
        Optional<QuoteData> optionalQuoteData = quoteDataRepository.findById(quoteId);
        if (optionalQuoteData.isPresent()) {
            QuoteData quoteData = optionalQuoteData.get();
            int increment = isUpVote ? 1 : -1;
            quoteData.setVoteRecorder(quoteData.getVoteRecorder() + increment);

            // Сохраняем обновленные данные цитаты в репозитории
            quoteDataRepository.save(quoteData);
        } else {
            // Обработка случая, когда цитата не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
