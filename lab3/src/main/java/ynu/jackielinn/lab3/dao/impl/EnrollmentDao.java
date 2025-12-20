package ynu.jackielinn.lab3.dao.impl;

import jakarta.persistence.EntityManager;
import ynu.jackielinn.lab3.entity.Enrollment;
import ynu.jackielinn.lab3.util.JPAUtil;

import java.util.List;

/**
 * 选课 DAO：继承 BaseDao，并扩展自定义查询方法
 */
public class EnrollmentDao extends BaseDao<Enrollment, Long> {

    /**
     * 【选做题】JPQL 多表关联查询
     * 查询某老师名下分数 >= minScore 的选课记录，按分数降序
     */
    public List<Enrollment> findByTeacherAndMinScore(String teacher, double minScore) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL：面向对象的查询语言，直接操作实体和属性
            String jpql = """
                    select e from Enrollment e
                    join e.course c
                    where c.teacher = :teacher and e.score >= :minScore
                    order by e.score desc
                    """;
            return em.createQuery(jpql, Enrollment.class)
                    .setParameter("teacher", teacher)    // 命名参数绑定
                    .setParameter("minScore", minScore)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
