package app.common.details;

import app.common.validation.ValidateAbleFieldDescriptor;
import core.ItemFactory;

import javax.swing.*;
import java.util.Map;

public abstract class EditorInputsPanel<TModel, TFactory extends ItemFactory<TModel>> extends JPanel {
    public abstract Map<String, ValidateAbleFieldDescriptor> getFieldsMap();

    public abstract TFactory mapFormValuesToItemFactory(TFactory itemFactory);

    public abstract TFactory setDefaultValuesForNewItem(TFactory itemFactory);

    public abstract void mapItemValuesToForm(TFactory item);
}
