<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="history" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Price history">
  <p>
    History
  </p>

  <table>
    <thead>
    <tr>
      <td>Start date</td>
      <td>Price</td>
    </tr>
    </thead>
    <c:forEach var="his" items="${history}">
      <tr>
        <td>
          ${his.date}
        </td>
        <td class="price">
          <fmt:formatNumber value="${his.price}" type="currency" currencySymbol="${his.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>