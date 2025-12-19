import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        String url = ConfigUtil.getProperty("dbURL");
        String user = ConfigUtil.getProperty("userName");
        String password = ConfigUtil.getProperty("password");
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            System.err.println("数据库连接失败：" + e.getMessage());
            throw new RuntimeException("数据库连接失败", e);
        }
        return conn;
    }

    public static void close(Connection conn, Statement stmt,
                             PreparedStatement prestmt,
                             ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (prestmt != null) {
            try {
                prestmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
