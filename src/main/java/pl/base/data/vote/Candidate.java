package pl.base.data.vote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "candidate")
@Getter
@Setter
public class Candidate extends VotingEntity {

    @Column(name = "votes_count")
    private int votesCount;

    public void addVote() {
        votesCount++;
    }

    @Override
    public String toString() {
        return getName();
    }
}
