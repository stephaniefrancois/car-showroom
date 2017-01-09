package app.toolbar;

import common.IRaiseEvents;
import resources.ResourceProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ToolbarPanel extends JPanel
        implements ActionListener,
        IRaiseEvents<ToolbarListener> {

    private List<ToolbarItem> toolbarItems;
    private List<ToolbarListener> listeners;

    public ToolbarPanel() {
        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new FlowLayout(FlowLayout.CENTER));

        listeners = new ArrayList<>();
        toolbarItems = getToolbarItems();
        toolbarItems.forEach((toolbarItem) -> {
            JButton button = toolbarItem.getButton();
            add(button);
            button.addActionListener(this);
        });
    }

    private List<ToolbarItem> getToolbarItems() {
        return Arrays.asList(
                createMenuItem("ViewCars", "View Cars", ResourceProvider.getCarIcon()),
                createMenuItem("ViewSales", "View Sales", ResourceProvider.getSalesIcon()),
                createMenuItem("ViewReports", "View Reports", ResourceProvider.getReportsIcon()),
                createMenuItem("ViewSettings", "View Settings", ResourceProvider.getSettingsIcon())
        );
    }

    private ToolbarItem createMenuItem(String key, String text, ImageIcon imageIcon) {
        return new ToolbarItem(key, new JButton(text, imageIcon));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<ToolbarItem> items = toolbarItems
                .stream()
                .filter(ti -> ti.getButton().equals(e.getSource()))
                .collect(Collectors.toList());
        ToolbarItem sourceToolbarItem = items.get(0);

        if (listeners.size() > 0) {
            ToolbarItemClickedEvent event = new ToolbarItemClickedEvent(e.getSource(),
                    sourceToolbarItem.getKey());
            listeners.forEach(l -> l.toolbarItemClicked(event));
        }
    }

    @Override
    public void addListener(ToolbarListener listenerToAdd) {
        if (listeners.contains(listenerToAdd) == false) {
            listeners.add(listenerToAdd);
        }
    }

    @Override
    public void removeListener(ToolbarListener listenerToRemove) {
        if (listeners.contains(listenerToRemove)) {
            listeners.remove(listenerToRemove);
        }
    }

    private final class ToolbarItem {
        private final String key;
        private final JButton button;

        public ToolbarItem(String key, JButton button) {

            this.key = key;
            this.button = button;
        }

        public String getKey() {
            return key;
        }

        public JButton getButton() {
            return button;
        }
    }
}


