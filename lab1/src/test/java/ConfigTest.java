import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void testConfig() {
        System.out.println(ConfigUtil.getProperty("dbURL"));
        System.out.println(ConfigUtil.getProperty("userName"));
        System.out.println(ConfigUtil.getProperty("password"));
    }
}
