<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>AloTra - Trà sữa chuẩn vị</title>
</head>
<body>
<section class="hero py-5 mb-4">
    <div class="container py-5">
        <div class="row align-items-center gy-4">
            <div class="col-lg-6">
                <h1 class="display-5 fw-bold text-dark">AloTra – Thưởng thức trà sữa chuẩn vị mỗi ngày</h1>
                <p class="lead text-muted mt-3">Từ những ly trà sữa truyền thống đến các công thức sáng tạo, AloTra luôn sẵn sàng phục vụ bạn.</p>
                <a href="#menu" class="btn btn-alotra text-white px-4 py-2 mt-3">Khám phá menu</a>
            </div>
            <div class="col-lg-6 text-center">
                <img src="https://images.unsplash.com/photo-1580915411954-282cb1c8abca?w=800" alt="Trà sữa" class="img-fluid rounded-4 shadow">
            </div>
        </div>
    </div>
</section>

<section id="menu" class="container pb-5">
    <div class="d-flex flex-wrap align-items-center justify-content-between mb-4">
        <h2 class="fw-semibold">Menu hôm nay</h2>
        <form class="d-flex" method="get">
            <div class="input-group">
                <span class="input-group-text bg-white"><i class="bi bi-search"></i></span>
                <input type="text" class="form-control" name="keyword" placeholder="Tìm kiếm đang xây dựng..." disabled>
            </div>
        </form>
    </div>

    <div class="mb-4">
        <div class="nav nav-pills gap-2">
            <a class="btn btn-outline-dark ${empty activeCategory ? 'active' : ''}" href="${pageContext.request.contextPath}/home">Tất cả</a>
            <c:forEach items="${categories}" var="cate">
                <a class="btn btn-outline-dark ${activeCategory == cate.id ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/home?category=${cate.id}">
                    <c:if test="${not empty cate.icon}">
                        <img src="${pageContext.request.contextPath}/${cate.icon}" alt="${cate.name}" class="me-1" style="width:24px;height:24px;object-fit:cover;">
                    </c:if>
                    ${cate.name}
                </a>
            </c:forEach>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty drinks}">
            <div class="text-center py-5 text-muted">
                <h4>Danh sách món sẽ sớm cập nhật!</h4>
                <p>Hãy quay lại sau để khám phá các món mới của AloTra.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <c:forEach items="${drinks}" var="drink">
                    <div class="col-md-4 col-lg-3">
                        <div class="card border-0 shadow-sm h-100">
                            <c:choose>
                                <c:when test="${not empty drink.thumbnail}">
                                    <img src="${drink.thumbnail}" class="card-img-top" alt="${drink.name}">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://images.unsplash.com/photo-1511920170033-f8396924c348?w=600" class="card-img-top" alt="${drink.name}">
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${drink.name}</h5>
                                <p class="card-text text-muted flex-grow-1">${empty drink.description ? 'Một lựa chọn tuyệt vời cho ngày mới!' : drink.description}</p>
                                <div class="d-flex align-items-center justify-content-between">
                                    <span class="fw-bold text-primary"><fmt:formatNumber value="${drink.price}" pattern="#,##0₫"/></span>
                                    <button class="btn btn-sm btn-alotra text-white">Đặt ngay</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</section>
</body>
</html>
