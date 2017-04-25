<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>权限查看</title>
</head>
<body>
<shiro:authenticated>您已经登录<a href="/logout">退出</a>        <a href="/session/sessionList">在线列表</a></shiro:authenticated>
<shiro:notAuthenticated><shiro:user>当前为rememberMe 模式</shiro:user> <a href="/login.jsp"> 登录</a></shiro:notAuthenticated>
<br>
<br>
<shiro:hasRole name="admin">角色:管理员</shiro:hasRole>

<shiro:hasRole name="user">角色:普通用户</shiro:hasRole>
<br>
addUser权限:<shiro:hasPermission name="addUser">✔</shiro:hasPermission><br>
showUser权限:<shiro:hasPermission name="showUser">✔</shiro:hasPermission><br>
editUser权限:<shiro:hasPermission name="editUser">✔</shiro:hasPermission><br>
deleteUser权限:<shiro:hasPermission name="deleteUser">✔</shiro:hasPermission><br>
<br>
拥有 普通用户 and 管理员 角色: <a href="/user/userAdmin">验证</a><br>
拥有 普通用户 or 管理员 角色: <a href="/user/userOradmin">验证</a><br>
拥有 管理员 角色: <a href="/user/admin">验证</a><br>
拥有 普通用户 角色: <a href="/user/user">验证</a><br>
拥有 addUser 权限 <a href="/user/addUser">验证</a><br>

<br>
管理员权限: addUser showUser editUser deleteUser
<br>
<br>
普通用户: showUser
</body>
</html>