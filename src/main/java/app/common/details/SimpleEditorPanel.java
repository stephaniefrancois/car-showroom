package app.common.details;

import app.RootLogger;
import app.common.BasicEventArgs;
import app.common.validation.ValidationSummaryPanel;
import core.IHaveIdentifier;
import core.ItemBuilder;
import core.ItemFactoryProvider;
import core.stock.model.UnableToUpdateCarException;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SimpleEditorPanel<TModel extends IHaveIdentifier,
        TItemFactory extends ItemBuilder<TModel>> extends EditorPanel {

    private static final Logger log = RootLogger.get();
    private final JButton saveButton;
    private final JButton cancelButton;

    private final ItemFactoryProvider<TModel, TItemFactory> itemFactoryProvider;
    private TItemFactory itemFactory;
    private final EditorInputsPanel<TModel, TItemFactory> inputsPanel;
    private final ValidationSummaryPanel validationMessagesPanel;

    public SimpleEditorPanel(ItemFactoryProvider<TModel, TItemFactory> itemFactoryProvider,
                             EditorInputsPanel<TModel, TItemFactory> editorInputsPanel,
                             ValidationSummaryPanel validationMessagesPanel) {
        setLayout(new BorderLayout());

        this.itemFactoryProvider = itemFactoryProvider;
        this.itemFactory = itemFactoryProvider.createItemFactory();
        this.inputsPanel = editorInputsPanel;
        this.validationMessagesPanel = validationMessagesPanel;

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        controlsPanel.add(saveButton);
        controlsPanel.add(cancelButton);

        add(inputsPanel, BorderLayout.NORTH);
        add(validationMessagesPanel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        this.configureButtonHandlers();
    }

    private void configureButtonHandlers() {
        this.cancelButton.addActionListener(e -> cancelEditing(e));
        this.saveButton.addActionListener(e -> saveItem(e));
    }

    private void cancelEditing(ActionEvent e) {
        BasicEventArgs args = new BasicEventArgs(e.getSource(), this.itemFactory.getId());
        listeners.notifyListeners(l -> l.itemEditCancelled(args));
    }

    private void saveItem(ActionEvent e) {
        this.itemFactory = this.inputsPanel.mapFormValuesToItemFactory(this.itemFactory);

        ValidationSummary validationSummary = itemFactory.validate();
        this.validationMessagesPanel
                .displayValidationResults(validationSummary,
                        this.inputsPanel.getFieldsMap());

        if (validationSummary.getIsValid() == false) {
            logValidationFailed();
            return;
        }

        logValidationPassed();

        try {
            TModel item = itemFactory.build();
            item = saveItem(item);
            BasicEventArgs args = new BasicEventArgs(e.getSource(), item.getId());
            listeners.notifyListeners(l -> l.itemSaved(args));
        } catch (Exception ex) {
            logSaveFailed(ex);
        }
    }

    protected abstract TModel saveItem(TModel itemToSave) throws UnableToUpdateCarException;

    @Override
    public final void createItem() {
        logCreatingItem();
        this.validationMessagesPanel.clearValidationResults(this.inputsPanel.getFieldsMap());
        TItemFactory factory = this.itemFactoryProvider.createItemFactory();
        this.itemFactory = this.inputsPanel.setDefaultValuesForNewItem(factory);
        this.inputsPanel.mapItemValuesToForm(this.itemFactory);
    }

    @Override
    public final void editItem(int id) {
        this.validationMessagesPanel.clearValidationResults(this.inputsPanel.getFieldsMap());
        TModel itemToEdit = getItem(id);
        this.logEditItem(itemToEdit);
        this.itemFactory = this.itemFactoryProvider.createItemFactory(itemToEdit);
        this.inputsPanel.mapItemValuesToForm(this.itemFactory);
    }

    protected abstract TModel getItem(int id);

    private void logValidationFailed() {
        log.info(() -> String.format("Item with ID: '%d' is NOT valid!", itemFactory.getId()));
    }

    private void logValidationPassed() {
        log.info(() -> String.format("Item with ID: '%d' is valid.", itemFactory.getId()));
    }

    private void logSaveFailed(Exception ex) {
        log.log(Level.SEVERE, ex, () -> String.format("Failed to persist item changes.", itemFactory.getId()));
    }

    private void logCreatingItem() {
        log.info(() -> "Creating new item ...");
    }

    private void logEditItem(TModel itemToEdit) {
        log.info(() -> String.format("Editing item with id: %d", itemToEdit.getId()));
    }
}