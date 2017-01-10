package app.cars.details;

import app.cars.CarEventArgs;
import app.cars.carListing.CarListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CarDetailsPanel extends JPanel
        implements CarListener, CarDetailsListener, IRaiseEvents<CarDetailsListener> {

    private final CardLayout contentPresenter;
    private Map<String, JPanel> cards;

    public CarDetailsPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_CAR_DETAILS_PANEL_SIZE);
        setBorder(BorderStyles.getTitleBorder("Car details:"));
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);
        cards = configureContentPages();
        cards.forEach((key, panel) -> {
            add(panel, key);
        });
        noCarSelected();
    }

    private Map<String, JPanel> configureContentPages() {
        Map<String, JPanel> cards = new HashMap<>();
        cards.put(CarEditorPanel.class.getName(), new CarEditorPanel());
        cards.put(NoCarSelectedPanel.class.getName(), new NoCarSelectedPanel());
        cards.put(PreviewSelectedCarPanel.class.getName(), new PreviewSelectedCarPanel());
        return cards;
    }

    public void noCarSelected() {
        contentPresenter.show(this, NoCarSelectedPanel.class.getName());
    }

    public void previewSelectedCar(int carId) {
        String key = PreviewSelectedCarPanel.class.getName();
        PreviewSelectedCarPanel previewPanel = (PreviewSelectedCarPanel) cards.get(key);
        previewPanel.previewCar(carId);
        contentPresenter.show(this, key);
    }

    @Override
    public void carDeleted(CarEventArgs e) {
        this.noCarSelected();
    }

    @Override
    public void carSelected(CarEventArgs e) {
        this.previewSelectedCar(e.getCarId());
    }

    @Override
    public void carEditRequested(CarEventArgs e) {
        String key = CarEditorPanel.class.getName();
        CarEditorPanel editor = (CarEditorPanel) cards.get(key);
        editor.editCar(e.getCarId());
        contentPresenter.show(this, key);
    }

    @Override
    public void carModified(CarEventArgs e) {
        this.previewSelectedCar(e.getCarId());
    }

    @Override
    public void carEditCancelled(CarEventArgs e) {
        if (e.getCarId() == 0) {
            this.noCarSelected();
        } else {
            this.previewSelectedCar(e.getCarId());
        }
    }

    @Override
    public void addListener(CarDetailsListener listenerToAdd) {
        List<IRaiseEvents<CarDetailsListener>> eventProducers = this.getEventProducers();
        eventProducers.forEach(p -> p.addListener(listenerToAdd));
    }

    @Override
    public void removeListener(CarDetailsListener listenerToRemove) {
        List<IRaiseEvents<CarDetailsListener>> eventProducers = this.getEventProducers();
        eventProducers.forEach(p -> p.removeListener(listenerToRemove));
    }

    private List<IRaiseEvents<CarDetailsListener>> getEventProducers() {
        return Arrays.asList(
                (IRaiseEvents<CarDetailsListener>) cards.get(CarEditorPanel.class.getName()),
                (IRaiseEvents<CarDetailsListener>) cards.get(PreviewSelectedCarPanel.class.getName())
        );
    }
}
