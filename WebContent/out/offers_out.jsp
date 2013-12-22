<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="/categoria">HOME</a><br>

<c:forEach var="offer" items="${offers}">
    <tr>
      <p><b>${offer}</b></p>
    </tr>
</c:forEach>