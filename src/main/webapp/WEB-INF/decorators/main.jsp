<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sitemesh" uri="http://www.sitemesh.org/decorator" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title">AloTra - Trà sữa chuẩn vị</sitemesh:write></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600;700&display=swap">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <style>
        body {
            font-family: 'Be Vietnam Pro', sans-serif;
            background: #f9fafb;
        }
        .navbar-brand {
            font-weight: 700;
            font-size: 1.4rem;
        }
        .hero {
            background: radial-gradient(circle at top left, #fce7f3, #fff);
        }
        footer {
            background: #111827;
            color: #9ca3af;
        }
        .btn-alotra {
            background: linear-gradient(90deg, #ec4899, #f97316);
            border: none;
        }
        .btn-alotra:hover {
            opacity: .9;
        }
    </style>
    <sitemesh:write property="head"/>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
    <div class="container py-2">
        <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/home">
            AloTra
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <li class="nav-item"><a class="nav-link" href="#">Xin chào, ${sessionScope.account.fullName}</a></li>
                        <li class="nav-item"><a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<main class="flex-grow-1">
    <sitemesh:write property="body"/>
</main>

<footer class="mt-5 py-5">
    <div class="container">
        <div class="row gy-3">
            <div class="col-md-6">
                <h5 class="text-white">AloTra</h5>
                <p class="mb-0">Mang hương vị trà sữa chuẩn vị đến mọi nhà. Đặt hàng ngay để tận hưởng sự ngọt ngào!</p>
            </div>
            <div class="col-md-6 text-md-end">
                <p class="mb-1">Hotline: <a class="text-decoration-none text-white" href="tel:0123456789">0123 456 789</a></p>
                <p class="mb-0">© <fmt:formatDate value="${now}" pattern="yyyy"/> AloTra. All rights reserved.</p>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
