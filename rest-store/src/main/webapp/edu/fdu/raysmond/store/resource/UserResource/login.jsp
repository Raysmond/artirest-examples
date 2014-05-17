<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Login</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="user_name" class="col-sm-3 control-label">User
						Name</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="user_name"
							id="user_name" placeholder="User Name">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-3 control-label">Password</label>
					<div class="col-sm-9">
						<input type="password" class="form-control" name="password"
							id="password" placeholder="Password">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<a href="javascript:login()" class="btn btn-default">Login</a>
					</div>
				</div>
			</form>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<script>
    function login(){
    	$.ajax({
    		url: "/rest-store/user/login",
    		type: "post",
    		data: '{"user_name": "'+ $("#user_name").val() +'", "password":"'+ $("#password").val() +'"}',
    		dataType:"json",
    		contentType: "application/json",
    		success: function(response){
    			if(response.login_result == "success"){
    				window.location.href = "/rest-store/";
    			} else{
    				alert("Login failed.");
    			}
    		}
    	});
    }
    </script>

<%@ include file="/jsp/footer.jsp"%>