package ynu.jackielinn.lab5.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseDao<T, ID> extends JpaRepository<T, ID> {
}
