<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Loan detail</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<div class="col-sm-3">ID</div>
			<div class="col-sm-9">${it.id}</div>
			<div class="col-sm-3">Customer Name</div>
			<div class="col-sm-9">${it.customerName}</div>
			<div class="col-sm-3">Amount</div>
			<div class="col-sm-9">${it.amount}</div>
			<div class="col-sm-3">Status</div>
			<div class="col-sm-9">${it.state}</div>
			<div class="col-sm-3">Created time</div>
			<div class="col-sm-9">
				<fmt:formatDate value="${it.created}" pattern="yyyy-MM-dd HH:mm:ss" />
			</div>
			<div class="col-sm-3"></div>
			<div class="col-sm-9" style="padding-top: 10px;">
				<%
					//String admin = (String) session.getAttribute("USER_ROLE");
					//isAdmin = admin != null && admin.equals("admin");
				%>
				<c:choose>
					<c:when test="${it.state=='CREATED'}">
						<a class="btn btn-success" href="<c:url value="${it.id}/apply" />"
							title="Complete application">Complete application</a>
					</c:when>
					<c:when test="${it.state=='APPLIED' && isAdmin }">
						<a class="btn btn-success" href="<c:url value="loan/list" />"
							title="Approve">Approve</a>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>

<%@ include file="/jsp/footer.jsp"%>