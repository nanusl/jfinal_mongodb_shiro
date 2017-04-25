package cc.tuhaolicai.shiro.realm;

import cc.tuhaolicai.model.Role;
import cc.tuhaolicai.model.User;
import cc.tuhaolicai.shiro.token.SimpleUsernamePasswordToken;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.kit.StrKit;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

/**
 * @version V1.0
 * @Title： 自定义Realm
 * @Package： cc.tuhaolicai.shiro.realm
 * @Description： 鉴权，校验
 * @author： nan
 * @date： 2017-04-20 14:42
 */
public class ShiroDbRealm extends AuthorizingRealm {


    public ShiroDbRealm() {
        setAuthenticationTokenClass(SimpleUsernamePasswordToken.class);
    }

    /**
     * 认证回调函数,登录时调用.
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        SimpleUsernamePasswordToken authcToken = (SimpleUsernamePasswordToken) token;
        String username = authcToken.getUsername();
        MongoQuery mongoQuery = new MongoQuery().use("user");
        User user = mongoQuery.eq("username", username).findOne(User.class);
        if (null == user) {
            throw new UnknownAccountException("用户不存在");
        }
//        if (one.getBoolean("locked")) {
//            throw new LockedAccountException("该用户已被锁定，请联系客服人员！");
//        }
        SimpleAuthenticationInfo info;
        info = new SimpleAuthenticationInfo(new User(user.getId(), user.getUsername(), user.getDescription(), user.getType()), user.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(username + user.getSalt()));
        return info;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user1 = (User) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (null == user1) {
            return info;
        }
        MongoQuery mongoQuery = new MongoQuery().use("user");
        User one = mongoQuery.byId(user1.getId()).findOne(User.class);
        if (null == one) {
            return info;
        }
        List<Role> roles = one.getRoleList();
        if (null != roles && roles.size() > 0) {
            for (Role role : roles) {
                //角色的名称及时角色的值
                info.addRole(role.getRoleName());
                addResourceOfRole(role, info);
            }
        }
        return info;
    }

    private void addResourceOfRole(Role role, SimpleAuthorizationInfo info) {
        String resources = role.getResources();
        if (StrKit.notBlank(resources)) {
            for (String resource : resources.split(",")) {
                //资源代码就是权限值
                info.addStringPermission(resource);
            }
        }
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
