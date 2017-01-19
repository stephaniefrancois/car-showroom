package app;

import app.cars.CarsPanel;
import app.customers.CustomersPanel;
import app.sales.SalesPanel;
import app.styles.ComponentSizes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class ContentPanel extends JPanel {

    private final CardLayout contentPresenter;
    private Map<String, JPanel> cards;

    public ContentPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_CONTENT_PANEL_SIZE);
        setSize(getMinimumSize());
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);
        cards = configureContentPages();
        cards.forEach((key, panel) -> {
            add(panel, key);
        });
    }

    private Map<String, JPanel> configureContentPages() {
        Map<String, JPanel> cards = new HashMap<>();
        cards.put(CarsPanel.class.getName(), new CarsPanel());
        cards.put(CustomersPanel.class.getName(), new CustomersPanel());
        cards.put(SalesPanel.class.getName(), new SalesPanel());
        return cards;
    }

    public void navigateToCars() {
        navigateTo(CarsPanel.class.getName());
    }

    public void navigateToCustomers() {
        navigateTo(CustomersPanel.class.getName());
    }

    public void navigateToSales() {
        navigateTo(SalesPanel.class.getName());
    }

    private void navigateTo(String panelKey) {
        System.out.println("Navigating to '" + panelKey + "' ...");
        contentPresenter.show(this, panelKey);
    }
}
