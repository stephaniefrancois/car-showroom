package app.cars.details;

import app.cars.CarEventArgs;

import java.util.EventListener;

public interface CarDetailsListener extends EventListener {
    void carEditRequested(CarEventArgs e);

    void carSaved(CarEventArgs e);

    void carEditCancelled(CarEventArgs e);
}
