import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public int insert(Student student) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO student (name, sex, age, password) VALUES (?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setString(2, student.getSex());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getPassword());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

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

    @Override
    public Student findById(int id) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM student WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
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
