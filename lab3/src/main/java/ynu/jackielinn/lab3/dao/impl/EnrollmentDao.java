package ynu.jackielinn.lab3.dao.impl;

import jakarta.persistence.EntityManager;
import ynu.jackielinn.lab3.entity.Enrollment;
import ynu.jackielinn.lab3.util.JPAUtil;

import java.util.List;

public class EnrollmentDao extends BaseDao<Enrollment, Long> {

    // 选做题 JPQL：查询某老师名下“分数>=minScore”的选课记录，并按分数降序
    public List<Enrollment> findByTeacherAndMinScore(String teacher, double minScore) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                    select e from Enrollment e
                    join e.course c
                    where c.teacher = :teacher and e.score >= :minScore
                    order by e.score desc
                    """;
            return em.createQuery(jpql, Enrollment.class)
                    .setParameter("teacher", teacher)
                    .setParameter("minScore", minScore)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
