package app.common.listing;

public final class ListItem<TModel> {
    private final int index;

    public int getIndex() {
        return index;
    }

    public TModel getItem() {
        return item;
    }

    private final TModel item;

    public ListItem(int index, TModel item) {
        this.index = index;
        this.item = item;
    }
}
