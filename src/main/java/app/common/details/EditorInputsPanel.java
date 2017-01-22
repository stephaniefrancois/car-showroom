package app.common.details;

import app.common.validation.ValidateAbleFieldDescriptor;
import core.ItemBuilder;

import javax.swing.*;
import java.util.Map;

public abstract class EditorInputsPanel<TModel, TFactory extends ItemBuilder<TModel>> extends JPanel {
    public abstract Map<String, ValidateAbleFieldDescriptor> getFieldsMap();

    public abstract TFactory mapFormValuesToItemFactory(TFactory itemFactory);

    public abstract TFactory setDefaultValuesForNewItem(TFactory itemFactory);

    public abstract void mapItemValuesToForm(TFactory item);
}
