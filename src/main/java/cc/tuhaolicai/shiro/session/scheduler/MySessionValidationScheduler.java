package cc.tuhaolicai.shiro.session.scheduler;

import cc.tuhaolicai.util.SerializableUtils;
import cc.tuhaolicai.util.SpringUtils;
import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.page.MongoPaginate;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.AbstractValidatingSessionManager;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Title： MySessionValidationScheduler
 * @Package： cc.tuhaolicai.shiro.session.scheduler
 * @Description： 自定义Session调度器
 * @author： nan
 * @date： 2017-04-25 10:32
 */
public class MySessionValidationScheduler implements SessionValidationScheduler, Runnable {

    private static final Logger log = LoggerFactory.getLogger(MySessionValidationScheduler.class);

    ValidatingSessionManager sessionManager;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });
    private long interval = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;
    private boolean enabled = false;

    public MySessionValidationScheduler() {
        super();
    }

    public ValidatingSessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Creates a single thread {@link ScheduledExecutorService} to validate sessions at fixed intervals
     * and enables this scheduler. The executor is created as a daemon thread to allow JVM to shut down
     */
    //TODO Implement an integration test to test for jvm exit as part of the standalone example
    // (so we don't have to change the unit test execution model for the core module)
    public void enableSessionValidation() {
        if (this.interval > 0L) {
            this.service.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
            this.enabled = true;
        }
    }

    public void run() {
        log.info("Executing session validation...");
        long startTime = System.currentTimeMillis();

        int start = 1; //起始记录
        int size = 20; //每页大小
        //获取会话并验证
        MongoPaginate mongoQuery = new MongoPaginate(new MongoQuery().use("session"), size, start);
        List<JSONObject> jsonObjects = mongoQuery.findAll().getList();
        while (jsonObjects.size() > 0) {
            for (JSONObject jsonObject : jsonObjects) {
                try {
                    Session session = SerializableUtils.deserialize((String) jsonObject.get("session"));
                    Method validateMethod = SpringUtils.findMethod(AbstractValidatingSessionManager.class, "validate", Session.class, SessionKey.class);
                    validateMethod.setAccessible(true);
                    validateMethod.invoke(validateMethod, sessionManager, session, new DefaultSessionKey(session.getId()));
                } catch (Exception e) {
                    //ignore
                }
            }

            start = start + size;
            jsonObjects = new MongoPaginate(new MongoQuery().use("session"), size, start).findAll().getList();
        }
        long stopTime = System.currentTimeMillis();
        log.info("Session validation completed successfully in {} milliseconds.", (stopTime - startTime));
    }

    public void disableSessionValidation() {
        this.service.shutdownNow();
        this.enabled = false;
    }
}
