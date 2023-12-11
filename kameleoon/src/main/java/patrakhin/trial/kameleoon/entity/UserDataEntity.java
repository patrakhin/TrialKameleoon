package patrakhin.trial.kameleoon.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_data")
public class UserDataEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "e_mail", unique = true)
    private String eMail;

    @Column(name = "password")
    private String password;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @ManyToMany
    @JoinTable(
            name = "user_quote",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quote_id")
    )
    private List<QuoteDataEntity> quoteDatumEntities;

    public UserDataEntity(){}

    public UserDataEntity(String userName, String eMail, String password, Date creationDate, List<QuoteDataEntity> quoteDatumEntities) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.creationDate = creationDate;
        this.quoteDatumEntities = quoteDatumEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<QuoteDataEntity> getQuoteData() {
        return quoteDatumEntities;
    }

    public void setQuoteData(List<QuoteDataEntity> quoteDatumEntities) {
        this.quoteDatumEntities = quoteDatumEntities;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
