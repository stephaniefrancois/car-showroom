package core;

public interface ItemFactoryProvider<TModel, TItemFactory extends ItemBuilder<TModel>> {
    TItemFactory createItemFactory();

    TItemFactory createItemFactory(TModel item);
}
