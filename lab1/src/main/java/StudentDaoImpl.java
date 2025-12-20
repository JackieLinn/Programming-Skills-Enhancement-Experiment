import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDao 的 JDBC 实现类
 * 实现学生表的增删改查操作
 */
public class StudentDaoImpl implements StudentDao {

    /**
     * 新增学生（id 自增，不需要传入）
     * @return 影响的行数
     */
    @Override
    public int insert(Student student) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO student (name, sex, age, password) VALUES (?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            // 使用 ? 占位符防止 SQL 注入
            ps.setString(1, student.getName());
            ps.setString(2, student.getSex());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getPassword());
            return ps.executeUpdate();  // 执行更新，返回影响行数
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);  // 确保资源释放
        }
    }

    /**
     * 根据 id 删除学生
     */
    @Override
    public int deleteById(int id) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM student WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

    /**
     * 更新学生信息（根据 id 更新其他字段）
     */
    @Override
    public int update(Student student) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE student SET name=?, sex=?, age=?, password=? WHERE id=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setString(2, student.getSex());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getPassword());
            ps.setInt(5, student.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

    /**
     * 根据 id 查询单个学生
     * @return 学生对象，不存在则返回 null
     */
    @Override
    public Student findById(int id) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM student WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();  // 执行查询，返回结果集

            if (rs.next()) {
                // 从结果集中提取数据，封装为 Student 对象
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getInt("age"),
                        rs.getString("password")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, rs);
        }
    }

    /**
     * 根据性别查询学生列表
     */
    @Override
    public List<Student> findBySex(String sex) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM student WHERE sex = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sex);
            rs = ps.executeQuery();

            List<Student> list = new ArrayList<>();
            // 遍历结果集，将每条记录封装为对象
            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getInt("age"),
                        rs.getString("password")
                );
                list.add(s);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, rs);
        }
    }
}
