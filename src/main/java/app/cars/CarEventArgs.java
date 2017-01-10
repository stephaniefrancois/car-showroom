package app.cars;

import java.util.EventObject;

public final class CarEventArgs extends EventObject {
    private final int carId;

    public CarEventArgs(Object source, int carId) {
        super(source);
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }
}
