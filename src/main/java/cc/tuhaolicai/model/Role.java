package cc.tuhaolicai.model;

import com.cybermkd.mongo.kit.MongoBean;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title： 角色
 * @Package： cc.tuhaolicai.model
 * @Description： 存放shiro的role与Permission标识  重要！！！！
 * @author： nan
 * @date： 2017-04-20 11:05
 */
public class Role extends MongoBean implements Serializable{

    /**
     * role 标识
     */
    private String roleName;

    /**
     * Permission标识
     */
    private String resources;


    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
