package app.toolbar;

import app.RootLogger;
import app.styles.ButtonStyles;
import app.styles.ComponentSizes;
import common.IRaiseEvents;
import resources.ResourceProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class ToolbarPanel extends JPanel
        implements ActionListener,
        IRaiseEvents<ToolbarListener> {

    private static final Logger log = RootLogger.get();
    private final List<ToolbarItem> toolbarItems;
    private final List<ToolbarListener> listeners;
    private ToolbarItem activeToolbarItem;

    public ToolbarPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_TOOLBAR_SIZE);
        setSize(getMinimumSize());

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new FlowLayout(FlowLayout.CENTER));

        listeners = new ArrayList<>();
        toolbarItems = getToolbarItems();
        activeToolbarItem = null;
        toolbarItems.forEach((toolbarItem) -> {
            JButton button = toolbarItem.getButton();
            add(button);
            button.addActionListener(this);
        });
    }

    public void setActiveToolbarItem(String key) {
        List<ToolbarItem> matchingItems = this.toolbarItems
                .stream()
                .filter(t -> t.getKey().equals(key))
                .collect(Collectors.toList());

        if (matchingItems.size() > 0) {
            ToolbarItem toolbarItemToSelect = matchingItems.get(0);
            selectToolBarItem(toolbarItemToSelect.getButton(),
                    toolbarItemToSelect);
            logToolbarItemSelected(key);
        } else {
            logToolbarItemNotFound(key);
        }
    }

    private List<ToolbarItem> getToolbarItems() {
        return Arrays.asList(
                createMenuItem("ViewCars", "View Cars", ResourceProvider.getCarIcon(), true),
                createMenuItem("ViewCustomers", "View Customers", ResourceProvider.getCustomersIcon(), true),
                createMenuItem("ViewSales", "View Sales", ResourceProvider.getSalesIcon(), true),
                createMenuItem("ViewSettings", "View Settings", ResourceProvider.getSettingsIcon(), false)
        );
    }

    private ToolbarItem createMenuItem(String key, String text, ImageIcon imageIcon, boolean canBeActivated) {
        return new ToolbarItem(key, new JButton(text, imageIcon), canBeActivated);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<ToolbarItem> items = toolbarItems
                .stream()
                .filter(ti -> ti.getButton().equals(e.getSource()))
                .collect(Collectors.toList());

        selectToolBarItem(e.getSource(), items.get(0));
    }

    private void selectToolBarItem(Object source, ToolbarItem selectedToolbarItem) {
        deselectCurrentlyActiveItem();

        if (selectedToolbarItem.canBeActivated()) {
            selectedToolbarItem = selectCurrentToolbarItem(selectedToolbarItem);
        }

        this.activeToolbarItem = selectedToolbarItem;

        if (listeners.size() > 0) {
            ToolbarItemClickedEvent event = new ToolbarItemClickedEvent(source,
                    selectedToolbarItem.getKey());
            listeners.forEach(l -> l.toolbarItemClicked(event));
        }
    }

    private void deselectCurrentlyActiveItem() {
        if (activeToolbarItem != null) {
            activeToolbarItem
                    .getButton()
                    .setBorder(ButtonStyles.getDefaultButtonBorder());
        }
    }

    private ToolbarItem selectCurrentToolbarItem(ToolbarItem newlySelectedToolbarItem) {
        if (newlySelectedToolbarItem != null) {
            newlySelectedToolbarItem
                    .getButton()
                    .setBorder(ButtonStyles.getSelectedButtonBorder());
        }

        return newlySelectedToolbarItem;
    }

    @Override
    public void addListener(ToolbarListener listenerToAdd) {
        if (!listeners.contains(listenerToAdd)) {
            listeners.add(listenerToAdd);
        }
    }

    @Override
    public void removeListener(ToolbarListener listenerToRemove) {
        if (listeners.contains(listenerToRemove)) {
            listeners.remove(listenerToRemove);
        }
    }

    private void logToolbarItemSelected(String key) {
        log.info(() -> String.format("ToolbarItem with key '%s' have been activated.", key));
    }

    private void logToolbarItemNotFound(String key) {
        log.warning(() -> String.format("ToolbarItem with key '%s' has not been found!", key));
    }

    private final class ToolbarItem {
        private final String key;
        private final JButton button;
        private final boolean canBeActivated;

        public ToolbarItem(String key, JButton button, boolean canBeActivated) {

            this.key = key;
            this.button = button;
            this.canBeActivated = canBeActivated;
        }

        public String getKey() {
            return key;
        }

        public JButton getButton() {
            return button;
        }

        public boolean canBeActivated() {
            return canBeActivated;
        }
    }
}


