package app.sales.details.wizard;

import core.deal.model.CarDealDetails;

import java.util.EventObject;

public final class CarDealCompletedEventArgs extends EventObject {
    private final CarDealDetails carDeal;

    public CarDealCompletedEventArgs(Object source, CarDealDetails carDeal) {
        super(source);
        this.carDeal = carDeal;
    }

    public CarDealDetails getCarDeal() {
        return carDeal;
    }
}
