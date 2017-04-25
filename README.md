JFinal_MongoDB_Shiro_Demo
================================================
## 部署
    * 配置 jfinal.properties
    * 运行 clean compile jetty:run
    * 访问 http://localhost/

## 初始用户
    * 访问任意地址即可初始化用户
        * 管理员： admin 密码123456
        * 普通用户: user 密码123456

## 已完成功能
    * 使用非关系型数据库实现角色权限控制
    * 密码加密加盐校验
    * 密码错误计数累计5次锁定账户
    * 自动登录（并非真正的意义上的自动登录，只是配合过滤器可以允许一些浅权限。）
    * 在线会话管理
    * 强制踢出会话
    
## 未来将要实现的
    * 改用redis管理会话
    * 改用巨衫数据库
    * UI美化
    * 支持权限crud
    * zTree树引入
                                          
## shiro重要标签简介
    * 角色校验
        * JSP <shiro:hasRole name="admin"></shiro:hasRole>
        * JAVA @RequiresRoles("admin")
    * 权限校验
        * JSP <shiro:hasPermission name="addUser"></shiro:hasPermission><br>
        * JAVA @RequiresPermissions("addUser")
    * 登录校验
        * JSP <shiro:authenticated> <shiro:notAuthenticated>
        * JAVA @RequiresAuthentication

## 初始角色和权限标识
    * 角色：admin和user 
    * 权限标识：addUser、showUser、editUser、deleteUser
    
## 资料
       这个Demo参考了大量资料与代码，难免有BUG，欢迎大家修改，没有版权。
    * MongDB插件中文文档 https://t-baby.gitbooks.io/mongodb-plugin/
    * jfianl整合shiro
        * https://my.oschina.net/myaniu/blog/137198
        * https://my.oschina.net/myaniu/blog/137205
        * http://jinnianshilongnian.iteye.com/blog/2018398
                                                    ...(其他)
