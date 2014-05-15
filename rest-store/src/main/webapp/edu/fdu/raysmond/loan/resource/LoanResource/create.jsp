<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ include file="/jsp/header.jsp"%> 
    
    <div class="container">
	    <h1>Apply a new loan</h1>
	    <hr>
	    <div class="row">
	    	<div class="col-sm-6">
	    		<form class="form-horizontal" role="form" method="post" action="/rest-loan/loan/create_post">
				   <div class="form-group">
				    <label for="customer_name" class="col-sm-3 control-label">Your Name</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control" name="customer_name" id="customer_name" placeholder="Your Name">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="amount" class="col-sm-3 control-label">Amount</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control" name="amount" id="amount" placeholder="Amount">
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-3 col-sm-9">
				      <button type="submit" class="btn btn-success">Apply now</button>
				    </div>
				  </div>
				</form>
	    	</div>
	    	<div class="col-sm-6"></div>
	    </div>
    </div>
	
<%@ include file="/jsp/footer.jsp"%>