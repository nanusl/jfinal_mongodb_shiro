package cc.tuhaolicai.config;

import cc.tuhaolicai.controller.RootController;
import cc.tuhaolicai.controller.SessionController;
import cc.tuhaolicai.controller.UserController;
import cc.tuhaolicai.interceptor.InitInterceptor;
import cc.tuhaolicai.shiro.ShiroInterceptor;
import cc.tuhaolicai.shiro.ShiroPlugin;
import com.cybermkd.mongo.plugin.MongoJFinalPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

/**
 * @version V1.0
 * @Title： jfianl_config
 * @Package： cc.tuhaolicai.config
 * @Description： jfianl配置
 * @author： nan
 * @date： 2017-04-20 10:42
 */
public class MyConfig extends JFinalConfig {

    private Routes routes;

    @Override
    public void configConstant(Constants constants) {

        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("jfinal.properties");
        //设置是否是开发模式
        constants.setDevMode(PropKit.getBoolean("devMode", true));
        //设置视图类型
        constants.setViewType(ViewType.JSP);
        //设置404页面
        constants.setError404View("/404.jsp");
        //设置500页面
        constants.setError500View("/500.jsp");

    }

    @Override
    public void configRoute(Routes me) {

        this.routes = me;

        me.add("/", RootController.class);
        me.add("/user", UserController.class);
        me.add("/session", SessionController.class);

    }

    /**
     * 配置模版引擎
     *
     * @param me
     */
    @Override
    public void configEngine(Engine me) {
        System.out.println("MyConfig.configEngine");
    }

    @Override
    public void configPlugin(Plugins me) {

        // MongoDB插件
        MongoJFinalPlugin jFinalPlugin = new MongoJFinalPlugin();
        jFinalPlugin.add(PropKit.get("host"), PropKit.getInt("port"));
        jFinalPlugin.setDatabase(PropKit.get("database"));
        me.add(jFinalPlugin);

        // 加载Shiro插件
        me.add(new ShiroPlugin(routes));

        // 缓存插件
        me.add(new EhCachePlugin());

    }

    /**
     * 配置全局拦截器
     *
     * @param me
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new ShiroInterceptor());
        me.add(new InitInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
    }

}
