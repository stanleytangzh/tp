package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.PersonDataReceiver;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final PersonDataReceiver personDataReceiver;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, OverviewPanel overviewPanel) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personDataReceiver = new PersonDataReceiver(overviewPanel);

        // Add mouse click event listener
        personListView.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> handlePersonSelection(newValue));
    }

    /**
     * Handles the selection of a person in the list.
     *
     * @param selectedPerson The selected person in the ListView.
     */
    private void handlePersonSelection(Person selectedPerson) {
        if (selectedPerson != null) {
            sendDataToReceiver(selectedPerson);
        }
    }

    /**
     * Sends the selected person's data to the receiver.
     *
     * @param person The person whose data is being sent.
     */
    private void sendDataToReceiver(Person person) {
        personDataReceiver.receivePersonData(person);
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
