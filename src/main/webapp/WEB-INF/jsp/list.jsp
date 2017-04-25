<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
当前在线人数：${sessionCount}人<br/>
<table class="table">
    <thead>
        <tr>
            <th style="width: 300px;">会话ID</th>
            <th>主机地址</th>
            <th>最后访问时间</th>
            <th>已强制退出</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${sessions}" var="session">
            <tr>
                <td>${session.id}</td>
                <td>${session.host}</td>
                <td><fmt:formatDate value="${session.lastAccessTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>${session.attributes.status == null ? '否':'是'}</td>
                <td>
                    <c:if test="${null == session.attributes.status}">
                        <a href="${pageContext.request.contextPath}/session/forceLogout?_id=${session.id}">强制退出</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>