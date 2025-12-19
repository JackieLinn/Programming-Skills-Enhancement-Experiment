import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentJdbcTest {

    @Test
    public void testInsertStudents() {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO student (name, sex, age, password) VALUES (?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);

            // 新增一些数据（插入3条）
            ps.setString(1, "David");
            ps.setString(2, "male");
            ps.setInt(3, 23);
            ps.setString(4, "david123");
            ps.executeUpdate();

            ps.setString(1, "Eva");
            ps.setString(2, "female");
            ps.setInt(3, 19);
            ps.setString(4, "eva123");
            ps.executeUpdate();

            ps.setString(1, "Frank");
            ps.setString(2, "male");
            ps.setInt(3, 21);
            ps.setString(4, "frank123");
            ps.executeUpdate();

            System.out.println("插入完成");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

    @Test
    public void testDeleteOne() {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM student WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 3);
            int rows = ps.executeUpdate();
            System.out.println("删除影响行数：" + rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

    @Test
    public void testUpdateOne() {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE student SET age = ?, password = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 25);
            ps.setString(2, "newpwd");
            ps.setInt(3, 1);
            int rows = ps.executeUpdate();
            System.out.println("更新影响行数：" + rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, null);
        }
    }

    @Test
    public void testQueryBySex() {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM student WHERE sex = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "male");
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String password = rs.getString("password");

                System.out.println("id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", password=" + password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn, null, ps, rs);
        }
    }
}
