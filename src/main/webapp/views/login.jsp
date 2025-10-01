<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login</title></head>
<body>
<h3>Đăng nhập</h3>
  <form method="post" action="${pageContext.request.contextPath}/login">
    <label>User:</label>
    <input name="username"
           value="${cookie.remember_username.value != null ? cookie.remember_username.value : ''}"
           required>
    <label>Password:</label>
    <input name="password" type="password" required>
    <label><input type="checkbox" name="remember" value="1"> Remember me</label>
    <button type="submit">Sign in</button>
  </form>
<p><a href="register">Đăng ký</a></p>
</body>
</html>
