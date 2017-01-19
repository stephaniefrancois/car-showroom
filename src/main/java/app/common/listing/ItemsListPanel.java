package app.common.listing;

import app.RootLogger;
import app.common.BasicEventArgs;
import app.common.search.SearchEventArgs;
import app.common.search.SearchListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import core.IHaveIdentifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class ItemsListPanel<TModel extends IHaveIdentifier>
        extends JPanel implements IRaiseEvents<ListEventListener>, SearchListener {

    private static final Logger log = RootLogger.get();

    private static final String SHOW_ITEMS_PANEL_KEY = "ShowItemsPanelKey";
    private static final String ITEMS_NOT_FOUND_PANEL_KEY = "ItemsNotFoundPanelKey";
    private final TableModel<TModel> tableModel;
    private final JTable itemsTable;
    private final ListenersManager<ListEventListener> listeners;
    private final JPanel itemsContainerPanel;
    private final JPanel noItemsFoundPanel;
    private final JLabel noItemsFoundLabel;
    private final CardLayout contentPresenter;
    private final JButton addNewItemButton;
    private final ListOptions options;

    public ItemsListPanel(TableModel<TModel> tableModel,
                          ListOptions options) {
        Objects.requireNonNull(tableModel);
        Objects.requireNonNull(options);
        this.options = options;
        listeners = new ListenersManager<>();
        setMinimumSize(ComponentSizes.MINIMUM_LIST_PANEL_SIZE);
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        setBorder(BorderStyles.getTitleBorder(options.getTitle()));
        this.tableModel = tableModel;
        this.itemsTable = new JTable(tableModel);
        this.itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.itemsContainerPanel = new JPanel();
        this.itemsContainerPanel.setLayout(new BorderLayout());
        this.itemsContainerPanel.add(new JScrollPane(this.itemsTable), BorderLayout.CENTER);

        this.noItemsFoundLabel = new JLabel();
        this.noItemsFoundLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        this.addNewItemButton = new JButton("Add new ...");
        this.addNewItemButton.addActionListener(e -> listeners.notifyListeners(ListEventListener::itemCreationRequested));

        this.noItemsFoundPanel = new JPanel();
        this.noItemsFoundPanel.setLayout(new BorderLayout());
        JPanel noItemsContainer = new JPanel();
        noItemsContainer.setLayout(new FlowLayout());
        noItemsContainer.add(noItemsFoundLabel);
        noItemsContainer.add(addNewItemButton);
        this.noItemsFoundPanel.add(noItemsContainer);

        add(this.itemsContainerPanel, SHOW_ITEMS_PANEL_KEY);
        add(this.noItemsFoundPanel, ITEMS_NOT_FOUND_PANEL_KEY);
        this.configurePopupMenu();
    }

    public final void clearSelection() {
        this.itemsTable.clearSelection();
    }

    protected final void refresh() {
        List<TModel> items = this.getAllItems();
        if (items.isEmpty()) {
            this.addNewItemButton.setVisible(true);
            this.displayItemsNotFoundView(this.options.getNoItemsAvailableMessage());
            return;
        }

        this.tableModel.setData(items);
        this.tableModel.fireTableDataChanged();
        this.displayItemsView();
    }

    public final void selectItemById(int identifier) {
        List<ListItem<TModel>> items = this.tableModel.findById(identifier);
        if (items.isEmpty()) {
            this.clearSelection();
            logNoItemsMatched(identifier);
            return;
        }

        if (items.size() > 1) {
            this.clearSelection();
            logMultipleItemsMatched(identifier, items);
        }

        ListItem<TModel> itemToSelect = items.get(0);
        this.itemsTable.setRowSelectionInterval(itemToSelect.getIndex(), itemToSelect.getIndex());
        logItemSelected(itemToSelect);
    }

    protected abstract List<TModel> getAllItems();

    private void displayItemsNotFoundView(String message) {
        this.noItemsFoundLabel.setText(message);
        this.contentPresenter.show(this, ITEMS_NOT_FOUND_PANEL_KEY);
    }

    private void displayItemsView() {
        this.contentPresenter.show(this, SHOW_ITEMS_PANEL_KEY);
    }

    private void configurePopupMenu() {
        JPopupMenu menu = new JPopupMenu();

        if (this.options.allowToAddItem()) {
            JMenuItem addItemMenu = new JMenuItem("Add new ...");
            addItemMenu.addActionListener(arg ->
                    listeners.notifyListeners(ListEventListener::itemCreationRequested));
            menu.add(addItemMenu);
        }

        if (this.options.allowToDeleteItem()) {
            JMenuItem removeItemMenu = new JMenuItem("Delete selected ...");
            removeItemMenu.addActionListener(arg -> {
                int row = itemsTable.getSelectedRow();
                if (row > -1) {
                    TModel itemToDelete = tableModel.getValueAt(row);
                    askUserToDeleteSelectedItem(arg, row, itemToDelete);
                }
            });
            menu.add(removeItemMenu);
        }

        itemsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = itemsTable.rowAtPoint(e.getPoint());
                selectItemByRowIndex(e.getSource(), row);

                if (e.getButton() == MouseEvent.BUTTON3 && menu.getSubElements().length > 1) {
                    menu.show(itemsTable, e.getX(), e.getY());
                }
            }
        });

        itemsTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int row = itemsTable.getSelectedRow();
                    selectItemByRowIndex(e.getSource(), row);
                }
            }
        });
    }

    private void selectItemByRowIndex(Object source, int rowIndex) {

        itemsTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
        if (rowIndex > -1) {
            TModel item = tableModel.getValueAt(rowIndex);
            notifyListenersAboutSelectedItem(source, item);
        }
    }

    private void notifyListenersAboutSelectedItem(Object source, TModel item) {
        BasicEventArgs event = new BasicEventArgs(source, item.getId());
        listeners.notifyListeners(l -> l.itemSelected(event));
    }

    private void askUserToDeleteSelectedItem(ActionEvent arg0, int row, TModel itemToDelete) {
        int action = JOptionPane.showConfirmDialog(ItemsListPanel.this,
                getMessageForItemDeleteDialog(itemToDelete),
                "Confirm deletion", JOptionPane.YES_NO_OPTION);

        if (action == JOptionPane.YES_OPTION) {
            logDeletingItem(row);
            this.refresh();
            this.removeItem(itemToDelete);
            TableModel<TModel> tableModel = (TableModel<TModel>) itemsTable.getModel();
            tableModel.removeRow(row);
            notifyListenersAboutDeletedItem(arg0, itemToDelete);
            if (tableModel.getRowCount() == 0) {
                this.refresh();
            }
        }
    }

    protected abstract String getMessageForItemDeleteDialog(TModel item);

    protected abstract void removeItem(TModel itemToDelete);

    private void notifyListenersAboutDeletedItem(ActionEvent arg0, TModel deletedItem) {
        BasicEventArgs event = new BasicEventArgs(arg0.getSource(), deletedItem.getId());
        this.listeners.notifyListeners(l -> l.itemDeleted(event));
    }

    @Override
    public final void addListener(ListEventListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public final void removeListener(ListEventListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }

    @Override
    public final void search(SearchEventArgs e) {
        logSearching(e);
        List<TModel> items = this.findItems(e.getSearchCriteria());
        if (items.isEmpty()) {
            this.addNewItemButton.setVisible(false);
            logNothingFound(e);
            this.displayItemsNotFoundView(String.format("No records found with search criteria '%s' ...", e.getSearchCriteria()));
            return;
        }
        logResultsFound(e, items);
        this.tableModel.setData(items);
        this.tableModel.fireTableDataChanged();
        this.displayItemsView();
    }

    protected abstract List<TModel> findItems(String searchCriteria);

    @Override
    public final void resetSearch() {
        this.refresh();
    }

    private void logDeletingItem(int row) {
        log.info(() -> String.format("Deleting table row at '%d' index ...", row));
    }

    private void logSearching(SearchEventArgs e) {
        log.info(() -> String.format("Searching for: %s ...", e.getSearchCriteria()));
    }

    private void logNothingFound(SearchEventArgs e) {
        log.info(() -> String.format("Nothing was found for '%s' search criteria!", e.getSearchCriteria()));
    }

    private void logResultsFound(SearchEventArgs e, List<TModel> items) {
        log.info(() -> String.format("'%d' results were found using '%s' search criteria.", items.size(), e.getSearchCriteria()));
    }

    private void logNoItemsMatched(int identifier) {
        log.warning(() -> String.format("No items have been found in TABLE with id of '%d'!", identifier));
    }

    private void logMultipleItemsMatched(int identifier, List<ListItem<TModel>> items) {
        log.warning(() -> String.format("'%d' items have been found in TABLE with id of '%d'. " +
                "Item ID must be UNIQUE. No items selected!", items.size(), identifier));
    }

    private void logItemSelected(ListItem<TModel> itemToSelect) {
        log.info(() -> String.format("Item at row '%d' has been selected.", itemToSelect.getIndex()));
    }
}
