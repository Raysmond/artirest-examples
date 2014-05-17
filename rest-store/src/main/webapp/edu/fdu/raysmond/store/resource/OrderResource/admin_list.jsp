<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/jsp/header.jsp"%>
<div class="container">
	<h1>Order administration</h1>
	<hr>
	<div class="row">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Customer Name</th>
					<th>Customer Address</th>
					<th>Items</th>
					<th>Total</th>
					<th>Bill date</th>
					<th>State</th>
					<th>Operation</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${it}">
					<tr>
						<td>${i.id}</td>
						<td>${i.customerName}</td>
						<td>${i.customerAddress}</td>
						<td>
						<c:forEach var="item" items="${i.items }">
							${item.name } (${item.price }) &nbsp;
						</c:forEach>
						</td>
						<td>${i.total }</td>
						<td>
						<fmt:formatDate value="${i.billDate}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>${i.state}</td>
						<td><a href="/rest-store/order/${i.id}" class="btn btn-success btn-xs">View</a> <c:choose>
								<c:when test="${i.state=='Billed'}">
									<a href="javascript:confirmOrder('/rest-store/order/${i.id}/confirm')" class="btn btn-success btn-xs">Confirm</a>
								</c:when>
								<c:when test="${i.state=='Order_confirmed'}">
									<a href="/rest-store/order/${i.id}/shipment/create_shipping" class="btn btn-success btn-xs">Create Shipment</a>
								</c:when>
								<c:when test="${i.state=='Manager_creating_shipping'}">
									<a href="/rest-store/order/${i.id}/shipment/create_shipping" class="btn btn-success btn-xs">Shipment</a>
								</c:when>
								
								<c:when test="${i.state=='Ready_for_shipping'}">
									<a href="/rest-store/order/${i.id}/shipment/in_shipping" class="btn btn-success btn-xs">Set in shipping</a>
								</c:when>
								
								<c:when test="${i.state=='In_shipping'}">
									<a href="/rest-store/order/${i.id}/shipment/shipped" class="btn btn-success btn-xs">Shipped</a>
								</c:when>
								
								<c:when test="${i.state=='Shipped'}">
									<span class="btn btn-default btn-xs">Completed</span>
								</c:when>
								
								<c:otherwise>

								</c:otherwise>
							</c:choose> <a class="btn btn-danger btn-xs" href="javascript:remove('/rest-store/order/${i.id}')">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script>
	function approve(uri) {
		$.ajax({
			type : "PUT",
			url : uri,
			data : '{"approve": "yes"}',
			contentType : "application/json",
			success : function(response, status) {
				alert(response);
				window.location.reload();
			}
		});
	}
	function cancel(uri) {
		$.ajax({
			type : "PUT",
			url : uri,
			data : '{"approve": "no"}',
			contentType : "application/json",
			success : function(response, status) {
				alert(response);
				window.location.reload();
			}
		});
	}

	function remove(uri) {
		$.ajax({
			type : 'DELETE',
			url : uri,
			contentType : "application/json",
			success : function(response, status) {
				window.location.reload();
			}
		});
	}
</script>
<%@ include file="/jsp/footer.jsp"%>