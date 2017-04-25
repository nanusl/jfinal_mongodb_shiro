package cc.tuhaolicai.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.cybermkd.mongo.kit.MongoBean;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @Title： user
 * @Package： cc.tuhaolicai.model
 * @Description： user
 * @author： nan
 * @date： 2017-04-20 11:05
 */
public class User extends MongoBean implements Serializable {

    /**
     * 主键
     */
    private String _id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码 MD5
     */
    private String password;

    /**
     * shiro盐
     */
    private String salt;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private String type;
    /**
     * 角色名(角色描述，非shiro的role)
     */
    private String roleName;
    /**
     * 是否锁定
     */
    private Boolean locked;
    /**
     * 角色列表 一个用户可以有多个角色
     */
    private List<Role> roleList;

    public User() {
    }


    public User(String id, String username, String description, String type) {
        super();
        this._id = id;
        this.username = username;
        this.description = description;
        this.type = type;
    }


    public String getId() {
        return _id;
    }

    @JSONField(name = "_id")
    public void setId(String id) {
        this._id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
