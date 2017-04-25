package cc.tuhaolicai.shiro.session.filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @version V1.0
 * @Title： OnlineSessionFilter
 * @Package： cc.tuhaolicai.shiro.session.filter
 * @Description： 在线会话过滤
 * @author： nan
 * @date： 2017-04-25 10:27
 */
public class OnlineSessionFilter extends AccessControlFilter {

    /**
     * 强制踢出后重定向的地址
     */
    private String forceLogoutUrl;

    private SessionDAO sessionDAO;

    private EhCacheManager cacheManager;

    public String getForceLogoutUrl() {
        return forceLogoutUrl;
    }

    public EhCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(EhCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setForceLogoutUrl(String forceLogoutUrl) {
        this.forceLogoutUrl = forceLogoutUrl;
    }

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);

        if (subject == null || subject.getSession(false) == null) {
            return true;
        }
        Session session = sessionDAO.readSession(subject.getSession().getId());

        return session.getAttribute("status") == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            getSubject(request, response).logout();//强制退出
        } catch (Exception e) {/*ignore exception*/}

        //String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(request, response, getForceLogoutUrl());
        //false表示不需要后续处理
        return false;
    }
}
