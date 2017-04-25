package cc.tuhaolicai.controller;

import com.jfinal.core.Controller;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * @version V1.0
 * @Title： UserController
 * @Package： cc.tuhaolicai.controller
 * @Description： UserController
 * @author： nan
 * @date： 2017-04-25 16:33
 */
public class UserController extends Controller {

    @RequiresRoles({"user", "admin"})
    public void userAdmin() {
        setAttr("message", "role:user,admin");
        render("/WEB-INF/jsp/message.jsp");

    }

    @RequiresRoles(value = {"user", "admin"}, logical = Logical.OR)
    public void userOradmin() {
        setAttr("message", "role:user or admin");
        render("/WEB-INF/jsp/message.jsp");
    }

    @RequiresRoles("admin")
    public void admin() {
        setAttr("message", "role:admin");
        render("/WEB-INF/jsp/message.jsp");
    }

    @RequiresRoles("user")
    public void user() {
        setAttr("message", "role:user");
        render("/WEB-INF/jsp/message.jsp");
    }
    @RequiresPermissions("addUser")
    public void addUser() {
        setAttr("message", "permission:addUser");
        render("/WEB-INF/jsp/message.jsp");
    }
}
