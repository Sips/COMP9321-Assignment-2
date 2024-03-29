<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="css" scope="request" value="body{background-color:#eee;}"/>

<t:column1>
	<t:alerts />
	
	<form class="form-signin" method="POST" action="login">
	   <h2 class="form-signin-heading">Please sign in</h2>
	   <input name="redirectUrl" type="hidden" value="${redirectUrl}">
	   <input name="username" type="text" class="form-control" placeholder="Username" autofocus="">
	   <input name="password" type="password" class="form-control" placeholder="Password">
	   <!-- <label class="checkbox">
	     <input type="checkbox" value="remember-me"> Remember me
	   </label> -->
	   <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	 </form>
</t:column1>