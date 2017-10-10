<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Approve Authorization</title>
    <link rel="stylesheet"           href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
</head>
<body>
<br/>
<br/>
<br/>
<div class="container" class="mx-auto" >

	    <div class="alert alert-success" role="alert">
		    <h4>Confirm Access to Protected Resource</h4> 
		    <hr/>
		    <b>${request.clientId}</b> has requested access to following scopes:
		</div>
		
		<ul class="list-group">
		<c:forEach var="scope" items="${request.scope}">
			<li class="list-group-item">${scope}</li>
		</c:forEach>
		</ul>
	
	
		<c:url value="/oauth/authorize" var="authorizationUrl">
			<c:forEach var="scope" items="${request.scope}">
				<c:param name="scope.${scope}" value="true" />
			</c:forEach>
		</c:url>
	
		<form action='${authorizationUrl}' method='post'>
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input id="user_oauth_approval" name="user_oauth_approval" value="true" type="hidden" />
			<input name="authorize" value="Authorize" type="submit" onclick="$('#user_oauth_approval').attr('value',true)" class="btn btn-success btn-large" /> 
		</form>
</div>
</body>
</html>