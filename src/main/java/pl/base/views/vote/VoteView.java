package pl.base.views.vote;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.dao.OptimisticLockingFailureException;
import pl.base.data.vote.Candidate;
import pl.base.data.vote.Voter;
import pl.base.services.VotingService;
import pl.base.views.CustomNotification;
import pl.base.views.MainLayout;

@PageTitle("Vote")
@Route(value = "vote", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("USER")
public class VoteView extends HorizontalLayout {

    private final Select<Voter> voterSelect;
    private final Select<Candidate> candidateSelect;

    VotingService votingService;

    public VoteView(VotingService service) {
        this.votingService = service;
        voterSelect = new Select<>();
        candidateSelect = new Select<>();

        voterSelect.setPlaceholder("I'm");
        voterSelect.setItems(votingService.getVotersWhoNotVoted());

        candidateSelect.setPlaceholder("I vote for");
        candidateSelect.setItems(votingService.getAllCandidates());

        Button voteButton = new Button("Vote!");
        voteButton.addClickListener(event -> {
            Voter selectedVoter = voterSelect.getValue();
            Candidate selectedCandidate = candidateSelect.getValue();
            if (selectedVoter != null && selectedCandidate != null) {
                selectedVoter.setVoted(true);
                selectedCandidate.addVote();
                try {
                    votingService.addVote(selectedCandidate, selectedVoter);
                    CustomNotification.createSuccessNotification("Vote made!");
                } catch (OptimisticLockingFailureException e) {
                    CustomNotification.createErrorNotification("Voter has already voted!!!");
                }
                reloadSelectLists();
            } else {
                CustomNotification.createInformationNotification("Please select a voter and a candidate.");
            }
        });
        voteButton.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, voterSelect, candidateSelect, voteButton);

        add(voterSelect, candidateSelect, voteButton);
    }

    /**
     * Reloads the select lists for voters and candidates.
     * Clears the current items in the select lists and sets new items based on the voting service.
     * The voter select list is populated with voters who have not voted.
     * The candidate select list is populated with all candidates.
     */
    private void reloadSelectLists() {
        voterSelect.clear();
        candidateSelect.clear();
        voterSelect.setItems(votingService.getVotersWhoNotVoted());
        candidateSelect.setItems(votingService.getAllCandidates());
    }

}
