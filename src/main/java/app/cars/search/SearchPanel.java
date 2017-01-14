package app.cars.search;

import app.common.ValidateAbleFieldDescriptor;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel implements IRaiseEvents<CarSearchListener> {

    private static final int MINIMUM_SEARCH_CRITERIA_LENGTH = 2;
    private final ListenersManager<CarSearchListener> listeners;

    public SearchPanel() {
        this.listeners = new ListenersManager<>();
        setMinimumSize(ComponentSizes.MINIMUM_CAR_SEARCH_PANEL_SIZE);
        setSize(getMinimumSize());
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(BorderStyles.getTitleBorder("Search:"));
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchInput = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton resetSearchButton = new JButton("Show All Cars");
        ValidateAbleFieldDescriptor searchFieldDescriptor = new ValidateAbleFieldDescriptor(searchLabel, searchInput);

        add(searchLabel);
        add(searchInput);
        add(searchButton);
        add(resetSearchButton);

        searchButton.addActionListener(e -> {
           String searchCriteria = searchInput.getText();
            if (searchCriteria == null || searchCriteria.trim().length() < MINIMUM_SEARCH_CRITERIA_LENGTH) {
                searchFieldDescriptor.markFieldAsInvalid(LabelStyles.getForegroundColorForInvalidFieldLabel(),
                        LabelStyles.getForegroundColorForInvalidField());

                // TODO: log error in search criteria
                System.err.println("Search criteria -> '" + searchCriteria + "' is NOT valid!");
                return;
            }

            searchFieldDescriptor.markFieldAsValid();
            CarSearchEventArgs args = new CarSearchEventArgs(e.getSource(), searchCriteria);
            this.listeners.notifyListeners(l -> l.searchForCars(args));
            resetSearchButton.setVisible(true);
        });

        resetSearchButton.setVisible(false);
        resetSearchButton.addActionListener(e -> {
            resetSearchButton.setVisible(false);
            searchInput.setText("");
            listeners.notifyListeners(l -> l.resetSearch());
        });
    }

    @Override
    public void addListener(CarSearchListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarSearchListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
