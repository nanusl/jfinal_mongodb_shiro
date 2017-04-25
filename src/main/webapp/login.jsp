<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
</head>
<body>
<form action="/loginAction" method="post">
    username:<input name="username" value="admin"><br>
    password:<input name="password" value="123456"><br>
    <input type="checkbox" name="remember" value="true">记住我？<br>
    <button type="submit">管理员登录</button>
</form>
<form action="/loginAction" method="post">
    username:<input name="username" value="user"><br>
    password:<input name="password" value="123456"><br>
    <input type="checkbox" name="remember" value="true">记住我？<br>
    <button type="submit">用户登录</button>
</form>
${msg}
</body>
</html>