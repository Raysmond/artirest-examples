<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ include file="/jsp/header.jsp"%> 
<script>window.location.href = "/rest-loan/loan/${it.id}";</script>
    <div class="container">
	    <h1>RESTful Loan Process Manangement</h1>
	    <div class="row">
	    	<a class="btn btn-success" href="/loan/${it.id}">View loan information</a>
	    </div>
    </div>	
<%@ include file="/jsp/footer.jsp"%>
