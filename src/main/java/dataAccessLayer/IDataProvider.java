package dataAccessLayer;

import java.util.List;

// TODO: lets think this one over, if we wanna user Repository abstraction here?!
public interface IDataProvider<TEntity> {
    List<TEntity> get();

}
