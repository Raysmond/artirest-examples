<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Shipment completion confirmation</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<p>
				<b>Order ID: ${it.orderId } </b>
			</p>
			<p>
				<a href="javascript:saveShipment(${it.orderId })"
					class="btn btn-success">Confirm</a>
			</p>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<script>
	function saveShipment(orderId) {
		$.ajax({
			url : "/rest-store/order/" + orderId + "/shipment/shipped",
			type : "PUT",
			data : '{"confirm":"yes"}',
			dataType : "json",
			contentType : "application/json",
			success : function(response) {
				if (response.result == "success") {
					window.location.href = "/rest-store/order/" + orderId;
				} else {
					alert("Failed to save shipment.");
				}
			}
		});
	}
</script>

<%@ include file="/jsp/footer.jsp"%>