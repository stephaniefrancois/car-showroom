package app;

import app.cars.CarsPanel;
import app.customers.CustomersPanel;
import app.sales.SalesPanel;
import app.styles.ComponentSizes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class ContainerPanel extends JPanel {

    private final CardLayout contentPresenter;
    private Map<String, ContentPanel> cards;

    public ContainerPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_CONTENT_PANEL_SIZE);
        setSize(getMinimumSize());
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);
        cards = configureContentPages();
        cards.forEach((key, panel) -> {
            add(panel, key);
        });
    }

    private Map<String, ContentPanel> configureContentPages() {
        Map<String, ContentPanel> cards = new HashMap<>();
        cards.put(CarsPanel.class.getName(), new CarsPanel());
        cards.put(CustomersPanel.class.getName(), new CustomersPanel());
        cards.put(SalesPanel.class.getName(), new SalesPanel());
        return cards;
    }

    public void navigateToCars() {
        this.cards.get(CarsPanel.class.getName()).activated();
        navigateTo(CarsPanel.class.getName());
    }

    public void navigateToCustomers() {
        this.cards.get(CustomersPanel.class.getName()).activated();
        navigateTo(CustomersPanel.class.getName());
    }

    public void navigateToSales() {
        this.cards.get(SalesPanel.class.getName()).activated();
        navigateTo(SalesPanel.class.getName());
    }

    private void navigateTo(String panelKey) {
        System.out.println("Navigating to '" + panelKey + "' ...");
        contentPresenter.show(this, panelKey);
    }
}
