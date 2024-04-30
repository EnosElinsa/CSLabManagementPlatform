package top.galaxyrockets.cslabmanagementplatform.domain.po;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import cn.hutool.core.annotation.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 *  A person in a particular role in the system.
 * </p>
 *
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique primary key.
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @Alias("编号")
    private Integer userId;

    /**
     * The role of a user. User roles include '系统管理员', '实验员', '教师' and '学生'.
     */
    @Alias("角色")
    private String role;

    /**
     * <p>The username of a user.
     * <p>For lab technicians and teachers, employee number is their username, and for students, student number is their username. As 
     * for system admins, their username does not have any particular meaning.
     * <p>The valid length of a username is within the range [4, 20].
     */
    @Alias("用户名")
    private String username;

    /**
     * <p>The password of a user.
     * <p>The valid length of a password is within the range [8, 128].
     */
    @Alias("密码")
    private String password;

    /**
     * The full name of a user in real life with the maximum length of 128 characters.
     */
    @Alias("姓名")
    private String fullName;

    /**
     * The job title of a teacher or a lab technician with the maximum length of 16 characters.
     */
    @Alias("职称")
    private String title;

    /**
     * The major of a student with the maximum length of 32 characters.
     */
    @Alias("专业")
    private String major;

    /**
     * The class of a student with the maximum length of 32 characters.
     */
    @Alias("班级")
    private String studentClass;

    /**
     * <p>The token of a user.
     * <p>The token is used to authenticate a user when they log in to the system.
     */
    @TableField(exist = false)
    private String token;

    /**
     * The logic delete field.
     */
    private Boolean deleted;

}
