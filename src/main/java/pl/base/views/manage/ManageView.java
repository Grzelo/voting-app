package pl.base.views.manage;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.addons.taefi.component.Vr;
import pl.base.data.vote.Candidate;
import pl.base.data.vote.Voter;
import pl.base.data.vote.VotingEntity;
import pl.base.services.VotingService;
import pl.base.views.MainLayout;

@PageTitle("Manage")
@Route(value = "manage-view", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManageView extends HorizontalLayout {

    VotingService votingService;
    Grid<Voter> votersGrid = new Grid<>();
    Grid<Candidate> candidatesGrid = new Grid<>();

    public ManageView(VotingService service) {
        this.votingService = service;
        setSpacing(false);
        add(createVotersLayout(), new Vr(), createCandidatesLayout());
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Creates a layout for displaying the voters.
     *
     * @return The created VerticalLayout.
     */
    private VerticalLayout createVotersLayout() {
        VerticalLayout votersLayout = new VerticalLayout();

        H3 votersH3 = new H3("Voters");
        votersH3.getStyle().set("margin", "0 auto 0 0");
        HorizontalLayout header = new HorizontalLayout(votersH3,
                createEntityButton("Add Voter", Voter.class));
        header.setAlignItems(Alignment.CENTER);
        header.getThemeList().clear();

        votersGrid.addColumn(Voter::getName).setHeader("Name");
        votersGrid.addColumn(new ComponentRenderer<>(voter -> {
            Checkbox checkbox = new Checkbox();
            checkbox.setValue(voter.hasVoted());
            checkbox.setEnabled(voter.hasVoted());
            checkbox.setReadOnly(true);
            return checkbox;
        })).setHeader("Has Voted");
        votersLayout.setAlignItems(Alignment.STRETCH);
        votersLayout.add(header, votersGrid);
        votersGrid.setItems(votingService.getAllVoters());
        return votersLayout;
    }

    /**
     * Creates the layout for the candidates section.
     *
     * @return The created VerticalLayout for the candidates section.
     */
    private VerticalLayout createCandidatesLayout() {
        VerticalLayout candidatesLayout = new VerticalLayout();

        H3 candidatesH3 = new H3("Candidates");
        candidatesH3.getStyle().set("margin", "0 auto 0 0");
        HorizontalLayout candidatesHeader = new HorizontalLayout(candidatesH3,
                createEntityButton("Add Candidate", Candidate.class));

        candidatesHeader.setAlignItems(Alignment.CENTER);
        candidatesHeader.getThemeList().clear();

        candidatesGrid.addColumn(Candidate::getName).setHeader("Name");
        candidatesGrid.addColumn(Candidate::getVotesCount).setHeader("Votes");

        candidatesLayout.setAlignItems(Alignment.STRETCH);
        candidatesLayout.add(candidatesHeader, candidatesGrid);

        candidatesGrid.setItems(votingService.getAllCandidates());
        return candidatesLayout;
    }

    /**
     * Creates a button for adding a new VotingEntity.
     *
     * @param buttonLabel the label for the button
     * @param entity      the VotingEntity to be added
     * @param <T>         a subclass of VotingEntity
     * @return the created Button
     */
    private <T extends VotingEntity> Button createEntityButton(String buttonLabel, Class<T> entity) {
        Button button = new Button(buttonLabel, VaadinIcon.PLUS_CIRCLE.create());
        button.addClickListener(clickEvent -> {
            VotingEntity votingEntity = entity == Voter.class ? new Voter() : new Candidate();
            String dialogHeader = votingEntity instanceof Voter ? "New Voter" : "New Candidate";
            Dialog dialog = new Dialog(dialogHeader);
            TextField newEntityName = new TextField("Name");
            newEntityName.focus();
            newEntityName.setRequired(true);
            var saveButton = new Button("Save", saveEvent -> {
                if (!newEntityName.getValue().isEmpty()) {
                    votingEntity.setName(newEntityName.getValue());
                    if (votingEntity instanceof Voter) {
                        votingService.addVoter((Voter) votingEntity);
                        votersGrid.setItems(votingService.getAllVoters());
                    } else {
                        votingService.addCandidate((Candidate) votingEntity);
                        candidatesGrid.setItems(votingService.getAllCandidates());

                    }
                    dialog.close();
                } else {
                    newEntityName.setErrorMessage("Fill name");
                    newEntityName.setInvalid(true);
                }
            });
            saveButton.addClickShortcut(Key.ENTER);
            var horizontalLayout = new HorizontalLayout(newEntityName, saveButton);
            horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
            dialog.add(horizontalLayout);
            dialog.open();
        });
        return button;
    }
}
