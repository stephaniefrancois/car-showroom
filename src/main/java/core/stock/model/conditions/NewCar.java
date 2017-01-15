package core.stock.model.conditions;

import core.stock.model.Condition;

public final class NewCar extends Condition {
    @Override
    public String getDescription() {
        return "NEW";
    }
}
