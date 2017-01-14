package app.cars.listing;

import app.cars.CarEventArgs;

import java.util.EventListener;

public interface CarListener extends EventListener {
    void carDeleted(CarEventArgs e);

    void carSelected(CarEventArgs e);

    void carCreationRequested();
}
