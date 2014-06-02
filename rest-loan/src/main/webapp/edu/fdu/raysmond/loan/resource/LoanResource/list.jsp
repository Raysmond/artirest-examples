<%@page import="edu.fdu.raysmond.loan.entity.LoanState"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/jsp/header.jsp"%>
<div class="container">
	<h1>Loan administration</h1>
	<hr>
	<div class="row">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>User Name</th>
					<th>Amount</th>
					<th>Bank</th>
					<th>Status</th>
					<th>Created</th>
					<th>Operation</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${it}">
					<tr>
						<td>${i.id}</td>
						<td>${i.customerName}</td>
						<td>${i.amount}</td>
						<td><c:choose>
								<c:when test="${i.bank!=null}">  
					                   ${i.bank}
					            </c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose></td>
						<td>${i.state}</td>
						<td><fmt:formatDate value="${i.created}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><a href="/rest-loan/loan/${i.id}"><span
								class="glyphicon glyphicon-search"></span></a> <c:choose>
								<c:when test="${i.state=='APPLIED'}">
									<a href="javascript:approve('/rest-loan/loan/${i.id}/approve')"
										title="Approve"><span class="glyphicon glyphicon-ok"></span></a>
									<a href="javascript:cancel('/rest-loan/loan/${i.id}/approve')"
										title="Cancel"><span class="glyphicon glyphicon-remove"></span></a>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose> <a href="javascript:remove('/rest-loan/loan/${i.id}')"><span
								class="glyphicon glyphicon-trash"></span></a></td>
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