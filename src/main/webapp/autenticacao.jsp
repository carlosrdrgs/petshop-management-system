<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

 <c:if test="${empty sessionScope.logado}">
 	<jsp:forward page="formulario-login.jsp"/>
 </c:if>