<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Bill</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<div class="row">
				<div class="col-sm-3" style="font-weight: bold;">Order ID</div>
				<div class="col-sm-9">${it.id }</div>
			</div>

			<div class="row">
				<div class="col-sm-3" style="font-weight: bold;">Total amount</div>
				<div class="col-sm-9">${it.total }</div>
			</div>

			<div class="row">
				<div class="col-sm-3" style="font-weight: bold;">Operation</div>
				<div class="col-sm-9">
					<a class="btn btn-success"
						href="javascript:confirmBill(${it.id },${it.total })">Complete
						bill</a>
				</div>
			</div>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<script>
	function confirmBill(orderId, total) {
		$.ajax({
			url : "/rest-store/order/" + orderId + "/bill",
			type : "PUT",
			data : '{"billed":"yes", "amount_paied": ' + total + '}',
			dataType : "json",
			contentType : "application/json",
			success : function(response, status) {
				if (response.result == "success") {
					window.location.href = "/rest-store/order/" + orderId;
				} else {
					alert("Fail to pay the bill.");
					window.location.reload();
				}
			}
		});
	}
</script>

<%@ include file="/jsp/footer.jsp"%>