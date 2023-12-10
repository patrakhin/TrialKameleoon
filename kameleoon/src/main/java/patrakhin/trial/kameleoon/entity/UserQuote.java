package patrakhin.trial.kameleoon.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_quote")
public class UserQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_data_id")
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "quote_data_id")
    private QuoteData quoteData;

    public UserQuote(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public QuoteData getQuoteData() {
        return quoteData;
    }

    public void setQuoteData(QuoteData quoteData) {
        this.quoteData = quoteData;
    }

    @Override
    public String toString() {
        return "UserQuote{" +
                "id=" + id +
                ", userData=" + userData +
                ", quoteData=" + quoteData +
                '}';
    }
}
