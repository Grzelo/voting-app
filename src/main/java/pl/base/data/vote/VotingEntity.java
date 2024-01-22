package pl.base.data.vote;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import pl.base.data.AbstractEntity;

import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
public abstract class VotingEntity extends AbstractEntity {
    private String name;

    @Column(name = "added_by")
    private String addedBy;

    @Column(name = "add_date")
    private LocalDateTime addDate;
}
