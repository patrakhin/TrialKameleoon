package patrakhin.trial.kameleoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import patrakhin.trial.kameleoon.entity.QuoteDataEntity;
import patrakhin.trial.kameleoon.entity.UserDataEntity;
import patrakhin.trial.kameleoon.repository.QuoteDataRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuoteDataService {

    private final QuoteDataRepository quoteDataRepository;
    private final Random random;

    @Autowired
    public QuoteDataService(QuoteDataRepository quoteDataRepository){
        this.quoteDataRepository = quoteDataRepository;
        this.random = new Random();
    }

    public QuoteDataEntity createQuoteData(String content){
        QuoteDataEntity quoteDataEntity = new QuoteDataEntity();
        quoteDataEntity.setContent(content);
        quoteDataEntity.setCreationDate(new Date());
        return quoteDataRepository.save(quoteDataEntity);
    }

    public Optional<QuoteDataEntity> getQuoteById(Long id) {
        return quoteDataRepository.findById(id);
    }

    public List<QuoteDataEntity> getAllQuotes() {
        return quoteDataRepository.findAll();
    }

    public QuoteDataEntity updateQuote(Long id, String newContent) {
        Optional<QuoteDataEntity> optionalQuote = quoteDataRepository.findById(id);
        if (optionalQuote.isPresent()) {
            QuoteDataEntity quote = optionalQuote.get();
            quote.setContent(newContent);
            quote.setUpdateDate(new Date());
            return quoteDataRepository.save(quote);
        } else {
            // Обработка случая, когда цитата с заданным id не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteQuote(UserDataEntity user, Long quoteId) {
        Optional<QuoteDataEntity> optionalQuote = quoteDataRepository.findById(quoteId);
        if (optionalQuote.isEmpty()) {
            // Цитата не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<UserDataEntity> users = optionalQuote.get().getUserData();
        boolean isOwnedByUser = users.stream().anyMatch(userData -> userData.getId() == user.getId());

        if (!isOwnedByUser) {
            // Непринадлежит пользователю
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quoteDataRepository.deleteById(quoteId);
    }

    public QuoteDataEntity getRandomQuote() {
        List<QuoteDataEntity> allQuotes = quoteDataRepository.findAll();
        if (!allQuotes.isEmpty()) {
            int randomIndex = random.nextInt(allQuotes.size());
            return allQuotes.get(randomIndex);
        } else {
            // Обработка случая, когда нет доступных цитат
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void voteForQuote(long quoteId, boolean isUpVote) {
        Optional<QuoteDataEntity> optionalQuoteData = quoteDataRepository.findById(quoteId);
        if (optionalQuoteData.isPresent()) {
            QuoteDataEntity quoteDataEntity = optionalQuoteData.get();
            int increment = isUpVote ? 1 : -1;
            quoteDataEntity.setVoteRecorder(quoteDataEntity.getVoteRecorder() + increment);

            // Сохраняем обновленные данные цитаты в репозитории
            quoteDataRepository.save(quoteDataEntity);
        } else {
            // Обработка случая, когда цитата не найдена
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
