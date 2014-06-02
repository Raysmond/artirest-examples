<%@page
	import="edu.fdu.raysmond.loan.controller.*, java.util.*,edu.fdu.raysmond.loan.entity.*"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/jsp/header.jsp"%>

<div class="container">
	<h1>Complete loan application</h1>
	<hr>
	<div class="row">
		<div class="col-sm-6">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="user_name" class="col-sm-3 control-label">User
						Name</label>
					<div class="col-sm-9">
						<p class="form-control-static">${it.customerName}</p>
					</div>
				</div>

				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">Amount</label>
					<div class="col-sm-9">
						<p class="form-control-static">${it.amount}</p>
					</div>
				</div>

				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">Created</label>
					<div class="col-sm-9">
						<p class="form-control-static">
							<fmt:formatDate value="${it.created}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</p>
					</div>
				</div>

				<div class="form-group">
					<label for="chosen_bank" class="col-sm-3 control-label">Banks</label>
					<div class="col-sm-9">
						<select id="chosen_bank" name="chosen_bank" class="form-control">
							<%
								Collection<String> all = BankController.controller().getAll();
							%>
							<c:forEach var="i" items="<%=all%>">
								<option value="${i}">${i}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<a
							href="javascript:complete('/rest-loan/loan/${it.id}/apply',${it.id})"
							class="btn btn-success">Complete</a>
					</div>
				</div>
			</form>
		</div>
		<div class="col-sm-6"></div>
	</div>
</div>
<script>
	function complete(uri, id) {
		var val = $("#chosen_bank").val();
		$.ajax({
			url : uri,
			type : 'PUT',
			data : '{"chosen_bank": "' + val + '"}',
			contentType : "application/json",
			success : function(response, status) {
				alert(response);
				window.location.href = "/rest-loan/loan/" + id;
			}
		});
	}
</script>
<%@include file="/jsp/footer.jsp"%>