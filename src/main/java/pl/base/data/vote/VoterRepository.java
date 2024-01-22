package pl.base.data.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoterRepository extends JpaRepository<Voter, Long>, JpaSpecificationExecutor<Voter> {
}
