package cc.tuhaolicai.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @version V1.0
 * @Title： 自定义异常
 * @Package： cc.tuhaolicai.shiro.exception
 * @Description：  用于友好提示
 * @author： nan
 * @date： 2017-04-20 14:42
 */
public class IncorrectCaptchaException extends AuthenticationException {

    private static final long serialVersionUID = -900348704002542821L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
