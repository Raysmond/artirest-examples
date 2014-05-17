<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Shipment</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="customer_name" class="col-sm-3 control-label">Order Id</label>
					<div class="col-sm-9">
						<p>${it.orderId }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="customer_name" class="col-sm-3 control-label">Receiver
						Name</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="customer_name"
							id="customer_name" placeholder="Receiver Name"
							value="${it.customerName }">
					</div>
				</div>
				<div class="form-group">
					<label for="customer_address" class="col-sm-3 control-label">Customer
						Address</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="customer_address"
							id="customer_address" placeholder="Customer Address"
							value="${it.shippingAddress }">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<a href="javascript:saveShipment(${it.orderId })"
							class="btn btn-success">Save</a>
					</div>
				</div>
			</form>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<script>
	function saveShipment(orderId) {
		$.ajax({
			url : "/rest-store/order/" + orderId + "/shipment/create_shipping",
			type : "PUT",
			data : '{"customer_name": "' + $("#customer_name").val()
					+ '", "customer_address":"' + $("#customer_address").val()
					+ '"}',
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