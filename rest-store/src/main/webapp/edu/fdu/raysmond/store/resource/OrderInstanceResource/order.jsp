<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Order detail</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Order detail</h3>
				</div>
				<div class="panel-body">
					<div class="col-sm-4" style="font-weight: bold;">ID</div>
					<div class="col-sm-8">${it.id}</div>
					<div class="col-sm-4" style="font-weight: bold;">Customer
						Name</div>
					<div class="col-sm-8">${it.customerName}</div>
					<div class="col-sm-4" style="font-weight: bold;">Total amount</div>
					<div class="col-sm-8">${it.total}</div>
					<div class="col-sm-4" style="font-weight: bold;">Status</div>
					<div class="col-sm-8">${it.state}</div>
					<div class="col-sm-4" style="font-weight: bold;">Bill</div>
					<div class="col-sm-8">
						<c:if test="${it.billDate!=null}">
							Paid at <fmt:formatDate value="${it.billDate}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</c:if>
						&nbsp;
					</div>
					<div class="col-sm-4" style="font-weight: bold;">Items</div>
					<div class="col-sm-8">
						<c:forEach var="i" items="${it.items}">
				${i.name} (${i.price}) &nbsp;
				</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Shipment and operations</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-4" style="font-weight: bold;">Shipment</div>
						<div class="col-sm-8">
							<c:if test="${it.state=='Customer_creating_shipping' }">
								<a class="btn btn-success"
									href="/rest-store/order/${it.id }/create_shipping">Edit
									shipment</a>
							</c:if>
							<p>
								<c:if test="${it.customerAddress!=null }">
					Receiver: ${it.customerName }<br />
					Address: ${it.customerAddress }
				</c:if>
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4" style="font-weight: bold;">Operation</div>
						<div class="col-sm-8">
							<c:if test="${it.state=='Adding_order_item' }">
								<a class="btn btn-success btn-xs"
									href="javascript:completeAddingItem('/rest-store/order/${it.id}/add_item_complete')">
									Complete adding items </a>
								<br />
							</c:if>
							<c:if
								test="${it.billDate==null && it.state=='Customer_creating_shipping' && it.customerAddress!=null}">
								<a href="/rest-store/order/${it.id}/bill"
									class="btn btn-success">Checkout</a>
							</c:if>
							<%
								String role = (String) request.getSession().getAttribute(
										"USER_ROLE");
								boolean isManager = role != null && role.equals("manager");
								if (isManager) {
							%>
							<c:if test="${it.state=='Billed' }">
								<a class="btn btn-success"
									href="/rest-store/order/${it.id }/confirm">Confirm
									(manager)</a>
							</c:if>
							<%
								}
							%>


						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function completeAddingItem(uri) {
		$.ajax({
			url : uri,
			type : "PUT",
			contentType : "application/json"
		}).done(function(response) {
			if (response.result == "fail") {
				alert("Error");
			}
			window.location.reload();
		});
	}
</script>

<%@ include file="/jsp/footer.jsp"%>