package ynu.jackielinn.lab3.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JPA 工具类：管理 EntityManagerFactory 生命周期
 */
public class JPAUtil {

    // EntityManagerFactory 是重量级对象，全局只创建一次
    private static final EntityManagerFactory emf;

    // 静态块：加载 persistence.xml 中的 "default" 持久化单元
    static {
        emf = Persistence.createEntityManagerFactory("default");
    }

    /**
     * 获取 EntityManager（每次操作需要新建，用完关闭）
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * 关闭工厂（应用关闭时调用）
     */
    public static void closeFactory() {
        emf.close();
    }
}
