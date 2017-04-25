package cc.tuhaolicai.controller;

import com.jfinal.core.Controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.util.Collection;

/**
 * @version V1.0
 * @Title： SessionController
 * @Package： cc.tuhaolicai.controller
 * @Description： SessionController
 * @author： nan
 * @date： 2017-04-25 16:34
 */
public class SessionController extends Controller {

    public void forceLogout() {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        SessionDAO sessionDAO = sessionManager.getSessionDAO();
        Session id = sessionDAO.readSession(getPara("_id"));
        id.setAttribute("status", "force_logout");
        redirect("/session/sessionList");
    }

    public void sessionList() {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        SessionDAO sessionDAO = sessionManager.getSessionDAO();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        setAttr("sessions", activeSessions);
        setAttr("sessionCount", sessionDAO.getActiveSessions().size());
        render("/WEB-INF/jsp/list.jsp");
    }
}
