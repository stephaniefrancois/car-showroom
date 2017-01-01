package core.domain.car.conditions;

import core.domain.car.Condition;

public final class NewCar extends Condition {
    @Override
    public String getDescription() {
        return "NEW";
    }
}
