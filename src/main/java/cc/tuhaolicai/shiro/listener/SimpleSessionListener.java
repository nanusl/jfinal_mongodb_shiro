package cc.tuhaolicai.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version V1.0
 * @Title： SessionListener
 * @Package： cc.tuhaolicai.shiro.listener
 * @Description： 会话监听器
 * @author： nan
 * @date： 2017-04-24 14:26
 */
public class SimpleSessionListener implements SessionListener {

    private static Logger logger = LoggerFactory.getLogger(SimpleSessionListener.class);

    @Override
    public void onStart(Session session) {
        logger.info("会话创建：{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.info("会话停止：{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("会话过期：{}", session.getId());
    }

}