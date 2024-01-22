package pl.base.services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.base.data.vote.Candidate;
import pl.base.data.vote.CandidateRepository;
import pl.base.data.vote.Voter;
import pl.base.data.vote.VoterRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotingService {

    VoterRepository voterRepository;
    CandidateRepository candidateRepository;

    public VotingService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public List<Voter> getVotersWhoNotVoted() {
        return voterRepository.findAll().stream()
                .filter(voter -> !voter.hasVoted())
                .collect(Collectors.toList());
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public void addVoter(Voter voter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            voter.setAddedBy(authentication.getName());
        }
        voter.setAddDate(LocalDateTime.now());
        voterRepository.saveAndFlush(voter);
    }

    public void addCandidate(Candidate candidate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            candidate.setAddedBy(authentication.getName());
        }
        candidate.setAddDate(LocalDateTime.now());
        candidateRepository.saveAndFlush(candidate);
    }

    @Transactional
    public void addVote(Candidate candidate, Voter voter) {
        voterRepository.saveAndFlush(voter);
        candidateRepository.saveAndFlush(candidate);
    }
}
