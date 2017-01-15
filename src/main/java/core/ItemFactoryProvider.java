package core;

public interface ItemFactoryProvider<TModel, TItemFactory extends ItemFactory<TModel>> {
    TItemFactory createItemFactory();

    TItemFactory createItemFactory(TModel item);
}
