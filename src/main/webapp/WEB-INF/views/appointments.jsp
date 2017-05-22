<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><s:message code="global.project.name" /></title>

<link href="static/vendors/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/bootstrap/css/bootstrapValidator.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/bootstrap/css/dataTables.bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css" type="text/css" rel="stylesheet">
<link href="../static/css/dashboard.css" type="text/css" rel="stylesheet">

<script src="static/vendors/jquery/jquery.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrapValidator.min.js"></script>
<script src="static/vendors/jquery/formValidation.min.js"></script>
<script src="static/vendors/jquery/framework/bootstrap.min.js"></script>
<script src="static/vendors/jquery/jquery.dataTables.min.js"></script>
<script src="static/vendors/jquery/dataTables.bootstrap.min.js"></script>
<script src="static/vendors/cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
<script src="static/js/moment.js"></script>
<script src="static/js/views/appointments.js"></script>
<script src="static/js/views/common.js"></script>
</head>

<c:set var="dateFormat">
	<s:message code="global.dateFormat" />
</c:set>

<body>
	<%@include file="include/top.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 main">
				<h3 align="center">
					<s:message code="${ pageHeader }" text="No header" />
				</h3>
				<%@include file="include/notifications.jsp"%>

				<!-- Users table -->
				<div class="row">
					<table id="mainTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th><s:message code="appointments.table.header.created" /></th>
								<th><s:message code="appointments.table.header.planned" /></th>
								<th><s:message code="appointments.table.header.patient" /></th>
								<th><s:message code="appointments.table.header.doctor" /></th>
								<th><s:message code="appointments.table.header.price" /></th>
								<th><s:message code="appointments.table.header.note" /></th>
								<th><s:message code="appointments.table.header.state" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="appointment" items="${ appointmentSet }">
								<tr id="appointmentId_${ appointment.id }" class='clickable clickable-row' onclick="getUser(${ appointment.id })">
									<td>${ appointment.id }</td>
									<td><nobr><fmt:formatDate value="${ appointment.created }" pattern='${ dateFormat }' /></nobr></td>
									<td><nobr><fmt:formatDate value="${ appointment.planned }" pattern='${ dateFormat }' /></nobr></td>
									<td>${ appointment.patient.firstName }</td>
									<td>${ appointment.doctor.firstName }</td>
									<td>${ appointment.price }</td>
									<td>${ appointment.note }</td>
									<td>
										<select class="selectpicker" data-style="btn-primary">
											<c:forEach var="status" items="${ appointmentStatusList }">
												<option value="${status}"<c:if test="${appointment.state == status}" > selected</c:if>>
													<s:message code="${status}" />
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

	$(document).ready(function()
		{$('#mainTable').DataTable({
			"order" : [ 4, 'asc' ],
			"dom" : 'Bfrtp',
			"language": {
				 "aria": {
				        "sortAscending":  '<s:message code="table.sort_ascending" />',
				        "sortDescending": '<s:message code="table.sort_descending" />'
				    },
					"decimal":        '<s:message code="table.decimal" />',
				    "emptyTable":     '<s:message code="table.empty_table" />',
				    "info":           '<s:message code="table.info" />',
				    "infoEmpty":      '<s:message code="table.info_empty" />',
				    "infoFiltered":   '<s:message code="table.info_filtered" />',
				    "infoPostFix":    '<s:message code="table.info_post_fix" />',
				    "lengthMenu":     '<s:message code="table.length_menu" />',
				    "loadingRecords": '<s:message code="table.loading_records" />',
				    "thousands":      '<s:message code="table.thousands" />',
				    "paginate": {
				        "first":      '<s:message code="table.first" />',
				        "last":       '<s:message code="table.last" />',
				        "next":       '<s:message code="table.next" />',
				        "previous":   '<s:message code="table.previous" />'
				    },
				    "processing":     '<s:message code="table.processing" />',
				    "search":         '<s:message code="table.search" />',
				    "zeroRecords":    '<s:message code="table.zero_records" />'
	        },
	        "buttons" :
				[{
					text: '<s:message code="users.button.user_create" />',
			        action: function ( e, dt, node, config ) {
			        	getUser(0);
				}}],

			"columnDefs":
			    [
			    	 { "type": "enum", targets: 1 },
	            ],

	         "pageLength": <s:message code="jsp.userListLength" />
			});
		});
	</script>
</body>
</html>
