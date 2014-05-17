<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Confirm</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<p>Order ID: ${it.id }</p>
			<p>Are you sure to approve the order?</p>
			<p>
				<a class="btn btn-success" href="javascript:approve(${it.id })">Approve
					the order</a>
			</p>

		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<script>
	function approve(orderId) {
		$.ajax({
			url : "/rest-store/order/" + orderId + "/confirm",
			type : "PUT",
			data : '{"confirm":"yes"}',
			dataType : "json",
			contentType : "application/json",
			success : function(response, status) {
				if (response.result != "success") {
					alert("Failed.");
				}
				window.location.href = "/rest-store/order/" + orderId;
			}
		});
	}
</script>

<%@ include file="/jsp/footer.jsp"%>