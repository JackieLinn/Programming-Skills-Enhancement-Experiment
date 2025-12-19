import org.junit.jupiter.api.Test;

import java.util.List;

public class StudentDaoTest {

    private final StudentDao studentDao = new StudentDaoImpl();

    @Test
    public void testInsert() {
        Student s = new Student(null, "Helen", "female", 22, "helen123");
        int rows = studentDao.insert(s);
        System.out.println("插入行数：" + rows);
    }

    @Test
    public void testUpdate() {
        Student s = new Student(1, "AliceUpdated", "female", 23, "newpwd");
        int rows = studentDao.update(s);
        System.out.println("更新行数：" + rows);
    }

    @Test
    public void testDelete() {
        int rows = studentDao.deleteById(2);
        System.out.println("删除行数：" + rows);
    }

    @Test
    public void testFindById() {
        Student s = studentDao.findById(1);
        System.out.println(s);
    }

    @Test
    public void testFindBySex() {
        List<Student> list = studentDao.findBySex("male");
        list.forEach(System.out::println);
    }
}
