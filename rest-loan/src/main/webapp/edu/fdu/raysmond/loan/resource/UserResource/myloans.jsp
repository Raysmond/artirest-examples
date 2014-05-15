<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/jsp/header.jsp"%>

<div class="container">
	<h1>My Loans</h1>
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
						<td>${i.user.name}</td>
						<td>${i.amount}</td>
						<td><c:choose>
								<c:when test="${i.bank!=null}">  
					                   ${i.bank.name}
					            </c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose></td>
						<td>${i.status}</td>
						<td><fmt:formatDate value="${i.created}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><a class="btn btn-info btn-xs" href="/loan/${i.id}">View</a>
							<c:choose>
								<c:when test="${i.status=='APPLYING'}">
									<a class="btn btn-info btn-xs" href="/loan/${i.id}/apply"
										title="Complete application">Complete</a>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


<%@include file="/jsp/footer.jsp"%>