<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="/categoria">HOME</a><br>
<b>CATEGORY OUT</b>
<table>
<c:forEach var="category" items="${categories}">
    <tr>
      <td><b><a href="categoria?id=${category.id}">${category.title}<${category.id}></a></b></td>
    </tr>
</c:forEach>
</table>
<!-- 
<table>
<c:forEach var="offer" items="${offers}">
    <tr>
   	  <td><b>${offer.id}</b></td>
      <td><b>${offer.title}</b></td>
    </tr>
</c:forEach>
</table>
 -->


