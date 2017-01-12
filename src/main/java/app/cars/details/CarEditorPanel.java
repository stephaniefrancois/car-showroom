package app.cars.details;

import app.cars.CarEventArgs;
import app.common.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.car.CarProperties;
import core.domain.validation.ValidationSummary;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class CarEditorPanel extends JPanel implements
        IRaiseEvents<CarDetailsListener> {

    private final JButton saveButton;
    private final JButton cancelButton;

    private final ListenersManager<CarDetailsListener> listeners;
    private final CarStock carStock;
    private final CarFactoryProvider carFactoryProvider;
    private final CarEditorInputsPanel inputsPanel;
    private final ValidationSummaryPanel validationMessagesPanel;
    private CarFactory carFactory;

    public CarEditorPanel() {
        setLayout(new BorderLayout());

        this.listeners = new ListenersManager<>();
        this.carStock = ServiceLocator.getComposer().getCarStockService();
        this.carFactoryProvider = ServiceLocator.getComposer().getCarFactoryProvider();
        this.carFactory = this.carFactoryProvider.createCarFactory();

        inputsPanel = new CarEditorInputsPanel();
        validationMessagesPanel = new ValidationSummaryPanel();

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        controlsPanel.add(saveButton);
        controlsPanel.add(cancelButton);

        add(inputsPanel, BorderLayout.NORTH);
        add(validationMessagesPanel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        this.configureButtonHandlers();
    }

    private void configureButtonHandlers() {
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelCarEditing(e);
            }
        });

        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCar(e);
            }
        });
    }

    private void cancelCarEditing(ActionEvent e) {
        CarEventArgs args = new CarEventArgs(e.getSource(), carFactory.getCarId());
        listeners.notifyListeners(l -> l.carEditCancelled(args));
    }

    private void saveCar(ActionEvent e) {
        this.carFactory = this.inputsPanel.mapFormValuesToCarFactory(this.carFactory);

        ValidationSummary validationSummary = carFactory.validate();
        this.validationMessagesPanel
                .displayValidationResults(validationSummary,
                        this.inputsPanel.getFieldsMap());

        if (validationSummary.getIsValid() == false) {
            System.err.println(String.format("Car with ID: '%d' is NOT valid!", carFactory.getCarId())); // TODO: log this
            return;
        }

        System.out.println(String.format("Car with ID: '%d' is valid ...", carFactory.getCarId()));

        try {
            CarProperties car = carFactory.build();
            if (car.getCarId() == 0) {
                car = carStock.addCar(car);
            } else {
                carStock.updateCar(car);
            }
            CarEventArgs args = new CarEventArgs(e.getSource(), carFactory.getCarId());
            listeners.notifyListeners(l -> l.carSaved(args));
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO: log error regarding car validation
        }
    }

    public void createCar() {
        this.carFactory = this.inputsPanel.setDefaultValuesForNewCar(
                this.carFactoryProvider.createCarFactory()
        );
        this.inputsPanel.mapCarValuesToForm(this.carFactory);
    }

    public void editCar(int carId) {
        System.out.println("Editing car with id: " + carId); // TODO: add logging statement about car being edited
        CarProperties car = this.carStock.getCarDetails(carId);
        this.carFactory = this.carFactoryProvider.createCarFactory(car);
        this.inputsPanel.mapCarValuesToForm(this.carFactory);
    }

    @Override
    public void addListener(CarDetailsListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarDetailsListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
