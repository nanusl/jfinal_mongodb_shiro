package cc.tuhaolicai.shiro.match;

import cc.tuhaolicai.util.EhcacheUtil;
import com.cybermkd.mongo.kit.MongoQuery;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V1.0
 * @Title： LimitRetryHashedMatcher
 * @Package： cc.tuhaolicai.shiro.match
 * @Description： 密码比较器
 * @author： nan
 * @date： 2017-04-24 14:26
 */
public class LimitRetryHashedMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //retrycount + 1
        Object element = EhcacheUtil.getItem(username);
        if (element == null) {
            EhcacheUtil.putItem(username, 1);
            element = 0;
            // 自动解锁
            new MongoQuery().use("user").eq("username", username).modify("locked", false).update();
        } else {
            int count = Integer.parseInt(element.toString()) + 1;
            element = count;
            EhcacheUtil.putItem(username, element);
        }
        AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString()));
        if (retryCount.incrementAndGet() > 5) {
            //if retrycount >5 throw
            new MongoQuery().use("user").eq("username", username).modify("locked", true).update();
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //clear retrycount
            EhcacheUtil.removeItem(username);
        }
        return matches;
    }
}