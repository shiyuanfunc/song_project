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
    <input type="submit" value="提交">
    <a href="${pageContext.request.contextPath}/download">下载</a>

</form>
</body>
</html>
