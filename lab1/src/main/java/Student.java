import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体类，对应数据库 student 表
 * @Data: 自动生成 getter/setter/toString/equals/hashCode
 * @NoArgsConstructor: 生成无参构造
 * @AllArgsConstructor: 生成全参构造
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;       // 主键，自增
    private String name;      // 姓名
    private String sex;       // 性别
    private Integer age;      // 年龄
    private String password;  // 密码
}
