package app.cars.search;

import app.styles.BorderStyles;
import app.styles.ComponentSizes;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    public SearchPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_CAR_SEARCH_PANEL_SIZE);
        setSize(getMinimumSize());
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(BorderStyles.getTitleBorder("Search:"));
        JTextField searchInput = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        JButton searchButton = new JButton("Search");

        add(new JLabel("Search:"));
        add(searchInput);
        add(searchButton);
    }
}
