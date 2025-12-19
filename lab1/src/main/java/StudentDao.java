import java.util.List;

public interface StudentDao {

    int insert(Student student);

    int deleteById(int id);

    int update(Student student);

    Student findById(int id);

    List<Student> findBySex(String sex);
}
