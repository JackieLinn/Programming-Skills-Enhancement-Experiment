package ynu.jackielinn.lab3.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 通用 DAO 接口
 * @param <T> 实体类型
 * @param <K> 主键类型（需实现 Serializable）
 */
public interface IBaseDao<T, K extends Serializable> {
    void save(T entity);          // 新增
    void update(T entity);        // 更新
    Optional<T> findById(K id);   // 根据主键查询
    List<T> findAll();            // 查询全部
    void delete(T entity);        // 删除
}
