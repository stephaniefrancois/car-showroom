package app.cars.details;

import app.cars.CarEventArgs;
import app.cars.listing.CarListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.EventProducersAggregate;
import common.IRaiseEvents;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public final class CarDetailsPanel extends JPanel
        implements CarListener, CarDetailsListener, IRaiseEvents<CarDetailsListener> {

    private final CardLayout contentPresenter;
    private final Pair<String, CarEditorPanel> carEditorView = new Pair<>(CarEditorPanel.class.getName(), new CarEditorPanel());
    private final Pair<String, NoCarSelectedPanel> noCarSelectedView = new Pair<>(NoCarSelectedPanel.class.getName(), new NoCarSelectedPanel());
    private final Pair<String, PreviewSelectedCarPanel> previewCarView = new Pair<>(PreviewSelectedCarPanel.class.getName(), new PreviewSelectedCarPanel());
    private final EventProducersAggregate<CarDetailsListener> eventProducers;

    public CarDetailsPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_DETAILS_PANEL_SIZE);
        setBorder(BorderStyles.getTitleBorder("Car details:"));
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        add(carEditorView.getValue(), carEditorView.getKey());
        add(noCarSelectedView.getValue(), noCarSelectedView.getKey());
        add(previewCarView.getValue(), previewCarView.getKey());

        navigateToNoCarSelected();

        carEditorView.getValue().addListener(this);
        previewCarView.getValue().addListener(this);

        eventProducers = new EventProducersAggregate<>(Arrays.asList(
                this.carEditorView.getValue(),
                this.previewCarView.getValue()
        ));
    }

    public void navigateToNoCarSelected() {
        contentPresenter.show(this, this.noCarSelectedView.getKey());
    }

    public void previewSelectedCar(int carId) {
        this.previewCarView.getValue().previewCar(carId);
        contentPresenter.show(this, this.previewCarView.getKey());
    }

    @Override
    public void carDeleted(CarEventArgs e) {
        this.navigateToNoCarSelected();
    }

    @Override
    public void carSelected(CarEventArgs e) {
        this.previewSelectedCar(e.getCarId());
    }

    @Override
    public void carCreationRequested() {
        this.carEditorView.getValue().createCar();
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void carEditRequested(CarEventArgs e) {
        this.carEditorView.getValue().editCar(e.getCarId());
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void carSaved(CarEventArgs e) {
        this.previewSelectedCar(e.getCarId());
    }

    @Override
    public void carEditCancelled(CarEventArgs e) {
        if (e.getCarId() == 0) {
            this.navigateToNoCarSelected();
        } else {
            this.previewSelectedCar(e.getCarId());
        }
    }

    @Override
    public void addListener(CarDetailsListener listenerToAdd) {
        this.eventProducers.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarDetailsListener listenerToRemove) {
        this.eventProducers.removeListener(listenerToRemove);
    }
}
