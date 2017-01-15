package core.stock.model.conditions;

import core.stock.model.Condition;

public class UsedCar extends Condition {
    @Override
    public String getDescription() {
        return "USED";
    }
}
