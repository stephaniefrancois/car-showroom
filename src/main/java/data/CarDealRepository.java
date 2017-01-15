package data;

import core.deal.model.CarDeal;
import core.deal.model.CarDealDetails;

import java.util.List;

public interface CarDealRepository {
    List<CarDeal> getDeals();

    CarDealDetails getDeal(int carDealId);

    CarDealDetails saveDeal(CarDealDetails deal);

    void removeDeal(int carDealId);

    List<CarDeal> findDeals(String searchCriteria);
}
