package ynu.jackielinn.lab3.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IBaseDao<T, K extends Serializable> {
    void save(T entity);

    void update(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    void delete(T entity);
}
