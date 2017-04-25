package cc.tuhaolicai.interceptor;

import cc.tuhaolicai.model.Role;
import cc.tuhaolicai.model.User;
import cc.tuhaolicai.util.EndecryptUtils;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title： InitInterceptor
 * @Package： cc.tuhaolicai.interceptor
 * @Description： 仅作初始化数据
 * @author： nan
 * @date： 2017-04-24 14:28
 */
public class InitInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void intercept(Invocation inv) {
        if (new MongoQuery().use("user").count() == 0) {
            User user = new User();
            user.setDescription("初始用户");
            user.setLocked(false);
            User cipherUser = EndecryptUtils.md5Password("admin", "123456");
            user.setPassword(cipherUser.getPassword());
            user.setSalt(cipherUser.getSalt());
            user.setUsername(cipherUser.getUsername());
            user.setRoleName("系统管理员");
            user.setType("技术部");

            User user1 = new User();
            user1.setDescription("初始用户");
            user1.setLocked(false);
            User cipherUser1 = EndecryptUtils.md5Password("user", "123456");
            user1.setPassword(cipherUser1.getPassword());
            user1.setSalt(cipherUser1.getSalt());
            user1.setUsername(cipherUser1.getUsername());
            user1.setRoleName("普通用户");
            user1.setType("技术部");

            //权限集
            String resourceList = "addUser,showUser,editUser,deleteUser";
            String resourceList1 = "showUser";


            //管理员角色
            Role role = new Role();
            role.setRoleName("admin");
            //管理员权限
            role.setResources(resourceList);

            //普通用户角色
            Role role1 = new Role();
            role1.setRoleName("user");
            //普通用户 只有查看权限
            role1.setResources(resourceList1);

            //角色集
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            roles.add(role1);

            List<Role> roles1 = new ArrayList<>();
            roles1.add(role1);

            user.setRoleList(roles);

            user1.setRoleList(roles1);

            boolean save = new MongoQuery().use("user").set(user).save();
            boolean save1 = new MongoQuery().use("user").set(user1).save();

            logger.info("初始化 admin 管理员 {}", save ? "成功！ 密码： 123456 " : "失败！");
            logger.info("初始化 user 普通用户 {}", save1 ? "成功！ 密码： 123456 " : "失败！");
        }

        inv.invoke();
    }
}
