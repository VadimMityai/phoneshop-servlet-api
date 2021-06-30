<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Advanced search">
    <p>
        Advanced search
    </p>
    <form>
        Model <input name="model" lab value="${param.model}">
        Mode <select name="mode">
        <option>All</option>
        <option>Any</option>
    </select>`
        Min price <input name="min" value="${param.min}">
        Max price <input name="max" value="${param.max}">
        <button>search</button>
    </form>
    <table>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
