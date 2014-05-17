<%@page
	import="edu.fdu.raysmond.store.entity.*, edu.fdu.raysmond.store.controller.*,java.util.*"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Online Shopping</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<%
				Integer orderId = (Integer) request.getSession().getAttribute(
																					"ORDER_ID");
																			if (orderId == null || orderId <= 0) {
			%>
			<p style="font-weight: bold;">Please input your name before you
				start shopping!</p>
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="customer_name" class="col-sm-3 control-label">Customer
						Name</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="customer_name"
							id="customer_name" placeholder="User Name">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<a href="javascript:newOrder()" class="btn btn-success">Start
							shopping</a>
					</div>
				</div>
			</form>
			<%
				} else {
			%>
			<a class="btn btn-success" href="/rest-store/order/<%=orderId%>">Current
				order (ID: <%=orderId%>)
			</a>
			<%
				}
			%>

		</div>
		<div class="col-sm-6">
			<%
				String customerName = (String) request.getSession().getAttribute("CUSTOMER_NAME");
											if(customerName!=null){
												Collection<Order> orders = OrderController.getOrders(customerName);
												if(!orders.isEmpty()){
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Your orders</h3>
				</div>
				<div class="panel-body">
					<%
						for(Order order: orders){
					%>
					<p>
						<a class="btn btn-info btn-xs"
							href="/rest-store/order/<%=order.getId()%>"> ID: <%=order.getId()%>
						</a> &nbsp; State:
						<%=order.getState()%>
						&nbsp;

						<%
							if(order.getState() == OrderState.Shipped){
						%>
						(Completed)
						<%
							} else if(order.getId()!=orderId){
						%>
						<a class="btn btn-success btn-xs"
							href="javascript:setCurrentOrder(<%=order.getId()%>)">Set as
							current order</a>
						<%
							}
						%>
					</p>
					<%
						}
					%>
				</div>
			</div>
			<%
				} }
			%>
		</div>
	</div>
</div>

<script>
	function newOrder() {
		$.ajax({
			url : "/rest-store/order",
			type : "post",
			data : '{"customer_name": "' + $("#customer_name").val() + '"}',
			dataType : "json",
			contentType : "application/json",
			statusCode : {
				201 : function(data) {
					window.location.reload();
				},
				403 : function(data) {
					alert(data);
				}
			}
		});
	}

	function setCurrentOrder(orderId) {
		$.ajax({
			url : "/rest-store/order/set_current_order",
			type : "PUT",
			data : '{"order_id": ' + orderId + '}',
			dataType : "json",
			contentType : "application/json",
			success : function(response, status) {
				if (response.result == "success") {
					window.location.reload();
				} else {
					alert("error");
				}
			}
		});
	}
</script>

<%@ include file="/jsp/footer.jsp"%>