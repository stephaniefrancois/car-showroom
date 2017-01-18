package app.sales.details;

import app.styles.BorderStyles;
import common.IRaiseEvents;
import common.ListenersManager;

import javax.swing.*;
import java.awt.*;

public final class CarDealWizardNavigationPanel extends JPanel implements IRaiseEvents<CarDealWizardNavigationEventListener> {
    private final ListenersManager<CarDealWizardNavigationEventListener> listeners;
    private final JButton goBackButton;
    private final JButton goForwardButton;
    private final JButton finishButton;

    public CarDealWizardNavigationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderStyles.getContentMargin());

        this.listeners = new ListenersManager<>();

        goBackButton = new JButton("< Previous");
        goForwardButton = new JButton("Next >");
        finishButton = new JButton("Finish!");

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        eastPanel.add(goForwardButton);
        eastPanel.add(finishButton);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        westPanel.add(goBackButton);

        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);

        goBackButton.addActionListener(e -> listeners.notifyListeners(l -> l.navigateBack()));
        goForwardButton.addActionListener(e -> listeners.notifyListeners(l -> l.navigateForward()));
        finishButton.addActionListener(e -> listeners.notifyListeners(l -> l.completeWizard()));
    }

    public void navigated(boolean canGoBack, boolean canGoForward, boolean isLastStep) {
        this.goBackButton.setVisible(canGoBack);
        if (isLastStep) {
            this.finishButton.setVisible(true);
            this.goForwardButton.setVisible(false);
        } else {
            this.finishButton.setVisible(false);
            this.goForwardButton.setVisible(canGoForward);
        }
    }

    @Override
    public void addListener(CarDealWizardNavigationEventListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarDealWizardNavigationEventListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
