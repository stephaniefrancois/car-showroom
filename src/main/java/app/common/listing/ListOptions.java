package app.common.listing;

public final class ListOptions {
    private final boolean allowToAddItem;
    private final boolean allowToDeleteItem;
    private final String title;
    private final String noItemsAvailableMessage;

    private ListOptions(boolean allowToAddItem, boolean allowToDeleteItem,
                        String title,
                        String noItemsAvailableMessage) {
        this.allowToAddItem = allowToAddItem;
        this.allowToDeleteItem = allowToDeleteItem;
        this.title = title;
        this.noItemsAvailableMessage = noItemsAvailableMessage;
    }

    public static ListOptions AllowEditingItems(String title,
                                                String noItemsAvailableMessage) {
        return new ListOptions(true, true, title, noItemsAvailableMessage);
    }

    public static ListOptions ReadOnly(String title,
                                       String noItemsAvailableMessage) {
        return new ListOptions(false, false, title, noItemsAvailableMessage);
    }

    public boolean allowToDeleteItem() {
        return allowToDeleteItem;
    }

    public boolean allowToAddItem() {
        return allowToAddItem;
    }

    public String getTitle() {
        return title;
    }

    public String getNoItemsAvailableMessage() {
        return noItemsAvailableMessage;
    }
}
