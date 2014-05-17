<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/jsp/header.jsp"%>
<div class="container">
	<h1>Items</h1>
	<hr>
	<div class="row">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Name</th>
					<th>Price</th>
					<th>Operation</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${it}">
					<tr>
						<td>${i.id}</td>
						<td>${i.name}</td>
						<td>${i.price}</td>
						<td>
							<%
								Integer orderId = (Integer) request.getSession().getAttribute(
											"ORDER_ID");
									if (null != orderId && orderId >= 0) {
										String uri = "/rest-store/order/" + orderId + "/add_item";
							%> 
							<a class="btn btn-success btn-xs"
							href="javascript:addItem('<%=uri%>', ${i.id})">Add item</a> <%
 	}
 %>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script>
	function addItem(uri, itemId) {
		$.ajax({
			type : "PUT",
			url : uri,
			data : '{"item_id": ' + itemId + '}',
			contentType : "application/json",
			success : function(response, status) {
				if (response.result == true) {
					alert("Add item to order successfully.");
					window.location.reload();
				} else
					alert("error");
			}
		});
	}
</script>
<%@ include file="/jsp/footer.jsp"%>