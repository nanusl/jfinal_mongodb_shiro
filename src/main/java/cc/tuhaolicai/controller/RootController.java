package cc.tuhaolicai.controller;

import cc.tuhaolicai.shiro.token.SimpleUsernamePasswordToken;
import cc.tuhaolicai.util.EhcacheUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

/**
 * @version V1.0
 * @Title： 根路由
 * @Package： controller
 * @Description： 根路由
 * @author： nan
 * @date： 2017-04-20 11:15
 */

public class RootController extends Controller {

    public void index() {
        render("/WEB-INF/jsp/index.jsp");
    }

    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        if (currentUser.isRemembered()) {
            currentUser.logout();
        }
        redirect("/index");
    }

    public void loginAction() {
        String username = getPara("username");
        String password = getPara("password");
        if (StrKit.notBlank(username) && StrKit.notBlank(password)) {
            Subject currentUser = SecurityUtils.getSubject();
            SimpleUsernamePasswordToken token;
            try {
                boolean remember = getParaToBoolean("remember");
                if (remember)
                    token = new SimpleUsernamePasswordToken(username, password, true);
                else
                    token = new SimpleUsernamePasswordToken(username, password);
            } catch (Exception e) {
                token = new SimpleUsernamePasswordToken(username, password);
            }
            try {
                currentUser.login(token);
            } catch (UnknownAccountException e) {
                setAttr("msg", "帐号不存在！");
            } catch (IncorrectCredentialsException e) {
                setAttr("msg", "密码错误！ 还可以尝试 " + (5 - Integer.parseInt(EhcacheUtil.getItem(username).toString())) + " 次！");
            } catch (ExcessiveAttemptsException e) {
                setAttr("msg", "密码输入错误5次，帐号已锁定 1分钟！");
            }
            if (currentUser.isAuthenticated()) {
                redirect("/index");
                return;
            }
        }
        forwardAction("/login");
    }

}
