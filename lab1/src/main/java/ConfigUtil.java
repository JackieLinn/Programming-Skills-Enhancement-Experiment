import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置工具类：读取 application.yml 配置文件
 */
public class ConfigUtil {

    private static final Properties properties = new Properties();

    // 静态代码块：类加载时读取配置文件到内存
    static {
        InputStream inputStream =
                ConfigUtil.class.getClassLoader().getResourceAsStream("application.yml");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 key 获取配置值
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
