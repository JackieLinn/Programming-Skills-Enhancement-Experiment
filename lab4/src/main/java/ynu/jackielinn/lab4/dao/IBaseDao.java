package ynu.jackielinn.lab4.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 通用 DAO 基接口
 * @NoRepositoryBean 表示此接口不需要 Spring 创建实例
 * JpaRepository 已提供 save/findById/findAll/delete 等方法
 */
@NoRepositoryBean
public interface IBaseDao<T, ID> extends JpaRepository<T, ID> {
}
