<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
<a href="regist.jsp">点击进入上传页面</a> <br/>
<a href="<s:url value="regist.jsp"/> ">第二种</a>
<a href="${pageContext.request.contextPath}/index">刷新</a>

<hr/>

<div>
    <c:if test="${not empty listUser}">
    <c:forEach items="${listUser}" var="user">
        <div>
            <span>${user.id}</span>
            <a href="${pageContext.request.contextPath}/download?user.id=${user.id}&&userName=${user.userName}&&path=${user.imgPath}">${user.userName}</a>
                <%--<span>${user.userName}</span>--%>
            <span>${user.passWord}</span>
            <span>${user.imgPath}</span>
            <img src="${pageContext.request.contextPath}/${user.imgPath}"/>
        </div>
    </c:forEach>
</div>
</c:if>
<c:if test="${empty listUser}">
    <div>
        暂无数据
    </div>

</c:if>

</body>
</html>
