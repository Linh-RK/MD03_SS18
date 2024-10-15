<%--
  Created by IntelliJ IDEA.
  User: phamlinh
  Date: 7/10/24
  Time: 08:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload file</title>
</head>
<body>
<form action="/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="image">
  <button>Upload</button>
</form>
</body>
</html>
