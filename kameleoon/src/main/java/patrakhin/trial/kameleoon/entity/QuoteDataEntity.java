package patrakhin.trial.kameleoon.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quote_data")
public class QuoteDataEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "vote_recorder", columnDefinition = "int default 0")
    private int voteRecorder;

    @ManyToMany(mappedBy = "quoteDatumEntities")
    private List<UserDataEntity> userDatumEntities;

    public QuoteDataEntity(){}

    public QuoteDataEntity(String content, Date creationDate, Date updateDate, int voteRecorder, List<UserDataEntity> userDatumEntities) {
        this.content = content;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.voteRecorder = voteRecorder;
        this.userDatumEntities = userDatumEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getVoteRecorder() {
        return voteRecorder;
    }

    public void setVoteRecorder(int voteRecorder) {
        this.voteRecorder = voteRecorder;
    }

    public List<UserDataEntity> getUserData() {
        return userDatumEntities;
    }

    public void setUserData(List<UserDataEntity> userDatumEntities) {
        this.userDatumEntities = userDatumEntities;
    }

    @Override
    public String toString() {
        return "QuoteData{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", voteRecorder=" + voteRecorder +
                '}';
    }
}
