<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ include file="/jsp/header.jsp"%> 
    
    <div class="container">
	    <h1>ArtiREST loan process example</h1>
	    <hr>
	    <div class="row">
	    	<div class="col-sm-6">
	    		<a href="/rest-loan/loan/create" class="btn btn-success">Apply a new loan</a>
	    	</div>
	    	<div class="col-sm-6"></div>
	    </div>
    </div>
	
<%@ include file="/jsp/footer.jsp"%>