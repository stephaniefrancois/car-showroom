package data;

import core.authentication.model.UserProfile;
import core.customer.model.Customer;
import core.deal.model.*;
import core.stock.model.CarDetails;
import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public final class InMemoryCarDealRepository implements CarDealRepository {
    private final CarDetails CAR_MB_S600 = new CarDetails(1, "Mercedes Benz", "S600",
            2017,
            "Black",
            new CarMetadata(1, "Petrol"),
            new CarMetadata(1, "Sedan"),
            new CarMetadata(2, "Automatic"),
            4,
            new BigDecimal(500000),
            Arrays.asList(new CarFeature(14, "Luxury Seats"),
                    new CarFeature(15, "Premium Leather"),
                    new CarFeature(16, "Champagne"),
                    new CarFeature(17, "W12 Bi-Turbo engine"))
    );

    private final CarDetails CAR_BENTLEY_GT = new CarDetails(2, "Bentley", "Continetal GT",
            2017,
            "Gray",
            new CarMetadata(1, "Petrol"),
            new CarMetadata(3, "Coupe"),
            new CarMetadata(2, "Automatic"),
            4,
            new BigDecimal(500000),
            Collections.singletonList(new CarFeature(14, "Luxury Seats"))
    );

    private final Customer CUSTOMER_SMITH = new Customer(4, "John", "Smith", "Trou aux biches",
            LocalDate.of(2000, 1, 1));
    private final SalesRepresentative SALES_JOE_BLACK = new SalesRepresentative(1,
            new UserProfile("Joe", "Black"));

    private final PaymentOptions PAYMENT_24_MONTHS = new PaymentOptions(24, LocalDate.now().minusDays(2));
    private final PaymentSchedule SCHEDULE_12_PAYMENTS = new PaymentSchedule(new BigDecimal(12000), Arrays.asList(
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now()),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(1)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(2)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(3)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(4)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(5)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(6)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(7)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(8)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(9)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(10)),
            new ScheduledPayment(new BigDecimal(1000), LocalDate.now().plusMonths(11))
    ));
    private final PaymentSchedule SCHEDULE_EMPTY = new PaymentSchedule(new BigDecimal(1000), new ArrayList<>());

    private ArrayList<CarDealDetails> deals = new ArrayList<>(Arrays.asList(
            new CarDealDetails(1, CAR_BENTLEY_GT, CUSTOMER_SMITH, LocalDate.now(), SALES_JOE_BLACK, PAYMENT_24_MONTHS, SCHEDULE_12_PAYMENTS),
            new CarDealDetails(2, CAR_MB_S600, CUSTOMER_SMITH, LocalDate.ofYearDay(2017, 10), SALES_JOE_BLACK, PAYMENT_24_MONTHS, SCHEDULE_EMPTY)
    ));

    @Override
    public List<CarDeal> getDeals() {
        return deals.stream().map(c -> new CarDeal(
                c.getId(),
                c.getCar().toString(),
                c.getCustomer().toString(),
                c.getDealDate(),
                c.getPaymentSchedule().getTotalAmountToPay(),
                c.getPaymentOptions().getDurationInMonths()
        )).collect(Collectors.toList());
    }

    @Override
    public CarDealDetails getDeal(int carDealId) {
        List<CarDealDetails> filteredDeals =
                deals.stream()
                        .filter(c -> c.getId() == carDealId)
                        .collect(Collectors.toList());

        if (filteredDeals.isEmpty()) {
            return null;
        }

        return filteredDeals.get(0);
    }

    @Override
    public CarDealDetails saveDeal(CarDealDetails deal) {
        if (deal.getId() == 0) {
            deal = addNewCarDeal(deal);
        } else {
            deal = updateExistingCarDeal(deal);
        }

        return deal;
    }

    private CarDealDetails addNewCarDeal(CarDealDetails car) {
        Optional<Integer> highestIndex =
                this.deals
                        .stream()
                        .map(CarDealDetails::getId).max(Integer::compare);
        int newCarIndex = 1;

        if (highestIndex.isPresent()) {
            newCarIndex = highestIndex.get() + 1;
        }

        CarDealDetails newCarDeal = new CarDealDetails(
                newCarIndex,
                car.getCar(),
                car.getCustomer(),
                car.getDealDate(),
                car.getSalesRepresentative(),
                car.getPaymentOptions(),
                car.getPaymentSchedule());

        this.deals.add(newCarDeal);
        return newCarDeal;
    }

    private CarDealDetails updateExistingCarDeal(CarDealDetails car) {
        List<CarDealDetails> filteredCars =
                deals.stream()
                        .filter(c -> c.getId() == car.getId())
                        .collect(Collectors.toList());
        int indexOfUpdatedCar = this.deals.indexOf(filteredCars.get(0));
        this.deals.set(indexOfUpdatedCar, car);
        return car;
    }

    @Override
    public void removeDeal(int carDealId) {
        List<CarDealDetails> dealsToDelete = this.deals.stream().filter(c -> c.getId() == carDealId).collect(Collectors.toList());

        for (CarDealDetails deal : dealsToDelete) {
            this.deals.remove(deal);
        }
    }

    @Override
    public List<CarDeal> findDeals(String searchCriteria) {
        return this.deals.stream()
                .filter(c -> c.getCar().toString().toLowerCase().contains(searchCriteria.toLowerCase()) ||
                        c.getCustomer().toString().toLowerCase().contains(searchCriteria.toLowerCase()))
                .map(c -> new CarDeal(
                        c.getId(),
                        c.getCar().toString(),
                        c.getCustomer().toString(),
                        c.getDealDate(),
                        c.getPaymentSchedule().getTotalAmountToPay(),
                        c.getPaymentOptions().getDurationInMonths()
                ))
                .collect(Collectors.toList());
    }
}
