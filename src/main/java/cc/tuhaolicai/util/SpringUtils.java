package cc.tuhaolicai.util;

import org.apache.shiro.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @version V1.0
 * @Title： SpringUtils
 * @Package： cc.tuhaolicai.util
 * @Description： 一些从Spring中提取的方法
 * @author： nan
 * @date： 2017-04-25 11:00
 */
public class SpringUtils {

    public static Method findMethod(Class<?> clazz, String name, Class... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");

        for (Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }

        return null;
    }
}
