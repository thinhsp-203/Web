<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký - AloTra</title>
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <h2 class="h4 fw-bold text-center mb-4">Tạo tài khoản AloTra</h2>
                    <c:if test="${not empty alert}">
                        <div class="alert alert-warning">${alert}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/register" method="post" class="row g-3 needs-validation" novalidate>
                        <div class="col-12">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label for="username" class="form-label">Tên đăng nhập</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label for="fullname" class="form-label">Họ và tên</label>
                            <input type="text" id="fullname" name="fullname" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label for="password" class="form-label">Mật khẩu</label>
                            <input type="password" id="password" name="password" class="form-control" required minlength="6">
                        </div>
                        <div class="col-md-6">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="text" id="phone" name="phone" class="form-control">
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-alotra text-white w-100">Đăng ký</button>
                        </div>
                    </form>
                    <p class="text-center mt-4 mb-0">Đã có tài khoản?
                        <a class="text-decoration-none" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
