package cc.tuhaolicai.shiro.token; /**
 * @version V1.0
 * @Title： token
 * @Package： shiro.token
 * @Description： 可以扩展
 * @author： nan
 * @date： 2017-04-20 14:42
 */

import org.apache.shiro.authc.UsernamePasswordToken;

public class SimpleUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 4676958151524148623L;


    public SimpleUsernamePasswordToken(String username, String password) {
        super(username, password, false);
    }

    public SimpleUsernamePasswordToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public SimpleUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}
