package app.cars.search;

import java.util.EventListener;

public interface CarSearchListener extends EventListener {
    void searchForCars(CarSearchEventArgs e);
    void resetSearch();
}
