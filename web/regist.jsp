<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="regist" method="post" enctype="multipart/form-data">
    <%--<label>用户头像</label><input type="text" name=""> <br/>--%>
    <s:file name="upload"/> <br/>
    用户名：<input type="text" name="user.userName" value=""/> <br/>
    密码：<input type="password" name="user.passWord" value=""/>
    <input type="submit" value="提交"/> <br/>
    <a href="${pageContext.request.contextPath}/download">下载</a>

</form>
</body>
</html>
