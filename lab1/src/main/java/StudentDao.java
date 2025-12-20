import java.util.List;

/**
 * 学生 DAO 接口：定义对 student 表的 CRUD 操作
 */
public interface StudentDao {

    int insert(Student student);       // 新增

    int deleteById(int id);            // 删除

    int update(Student student);       // 更新

    Student findById(int id);          // 根据 id 查询

    List<Student> findBySex(String sex);  // 根据性别查询
}
