package pl.base.data.vote;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voter")
@Getter
@Setter
public class Voter extends VotingEntity {
    private boolean voted;

    public boolean hasVoted() {
        return voted;
    }

    @Override
    public String toString() {
        return getName();
    }
}
