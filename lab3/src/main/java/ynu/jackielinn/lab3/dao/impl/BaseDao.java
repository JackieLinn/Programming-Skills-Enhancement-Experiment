package ynu.jackielinn.lab3.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ynu.jackielinn.lab3.dao.IBaseDao;
import ynu.jackielinn.lab3.util.JPAUtil;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * 通用 DAO 实现类（使用 JPA 进行持久化操作）
 * 子类继承后自动获得 CRUD 能力
 */
public class BaseDao<T, K extends Serializable> implements IBaseDao<T, K> {

    private final Class<T> clz;  // 实体类的 Class 对象

    /**
     * 通过反射获取泛型参数的实际类型
     */
    @SuppressWarnings("unchecked")
    public BaseDao() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    /**
     * 新增实体（persist 将新对象纳入持久化上下文）
     */
    @Override
    public void save(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    /**
     * 更新实体（merge 将游离对象合并到持久化上下文）
     */
    @Override
    public void update(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    /**
     * 根据主键查询（find 方法直接返回实体或 null）
     */
    @Override
    public Optional<T> findById(K id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(clz, id));
        } finally {
            em.close();
        }
    }

    /**
     * 查询全部（使用 JPQL 面向对象查询语言）
     */
    @Override
    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "select t from " + clz.getName() + " t";
            Query q = em.createQuery(jpql);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * 删除实体（需先 merge 变为托管态，再 remove）
     */
    @Override
    public void delete(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T managed = em.merge(entity);  // 游离态 -> 托管态
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
