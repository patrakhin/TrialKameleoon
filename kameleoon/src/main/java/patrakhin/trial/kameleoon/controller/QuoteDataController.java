package patrakhin.trial.kameleoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import patrakhin.trial.kameleoon.entity.QuoteDataEntity;
import patrakhin.trial.kameleoon.service.QuoteDataService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteDataController {

    private final QuoteDataService quoteDataService;

    @Autowired
    public QuoteDataController(QuoteDataService quoteDataService) {
        this.quoteDataService = quoteDataService;
    }

    @PostMapping
    public ResponseEntity<QuoteDataEntity> createQuote(@RequestBody QuoteDataEntity quoteDataEntity) {
        QuoteDataEntity createdQuote = quoteDataService.createQuoteData(quoteDataEntity.getContent());
        return new ResponseEntity<>(createdQuote, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDataEntity> getQuoteById(@PathVariable Long id) {
        return quoteDataService.getQuoteById(id)
                .map(quoteDataEntity -> new ResponseEntity<>(quoteDataEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<QuoteDataEntity>> getAllQuotes() {
        List<QuoteDataEntity> allQuotes = quoteDataService.getAllQuotes();
        return new ResponseEntity<>(allQuotes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteDataEntity> updateQuote(@PathVariable Long id, @RequestBody QuoteDataEntity quoteDataEntity) {
        QuoteDataEntity updatedQuote = quoteDataService.updateQuote(id, quoteDataEntity.getContent());
        return new ResponseEntity<>(updatedQuote, HttpStatus.OK);
    }

    /* Need UserData */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteQuote(@PathVariable Long id) {
//        quoteDataService.deleteQuote(/* UserData */, id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping("/random")
    public ResponseEntity<QuoteDataEntity> getRandomQuote() {
        try {
            QuoteDataEntity randomQuote = quoteDataService.getRandomQuote();
            return new ResponseEntity<>(randomQuote, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<String> voteForQuote(@PathVariable Long id, @RequestParam boolean isUpVote) {
        try {
            quoteDataService.voteForQuote(id, isUpVote);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
