package app.sales;

import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import app.sales.details.CarDealDetailsPanel;
import app.sales.listing.CarDealsListPanel;
import core.deal.model.CarDeal;

import javax.swing.*;
import java.awt.*;

public final class SalesPanel extends JPanel {
    private final SearchableListPanel<CarDeal> searchableCarDeals;
    private final CarDealDetailsPanel carDealDetails;

    public SalesPanel() {
        setLayout(new BorderLayout());

        this.searchableCarDeals = new SearchableListPanel(
                new SearchPanel(),
                new CarDealsListPanel()
        );
        this.carDealDetails = new CarDealDetailsPanel();
        add(this.searchableCarDeals, BorderLayout.CENTER);
        add(this.carDealDetails, BorderLayout.EAST);

        this.searchableCarDeals.addListener(this.carDealDetails);
        this.carDealDetails.addListener(this.searchableCarDeals);
    }
}
