<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:eval expression="T(accountant.util.SecurityHelper).isAdmin()" var="isAdmin" />

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><s:message code="global.project.name" text="Project name" /></a>
		</div>

		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${ includeAccountData }">
					<!-- li><form class="navbar-form navbar-right" method="GET" action="issuesSearch">
							<input type="text" name="text" class="form-control" placeholder="Search by Id and Subject...">
						</form></li -->
					<li><a href="<c:url value="/"/>"><s:message code="header.link.home" /></a></li>
					<c:if test="${ isAdmin }">
						<li><a href="<c:url value="/users"/>"><s:message code="header.link.users" /></a></li>
					</c:if>
					<li><a href="<c:url value="/appointments"/>"><s:message code="header.link.appointments" /></a></li>
					<li><a href="<c:url value="/account"/>"><s:message code="header.link.account" /></a></li>
					<li><a href="<c:url value="/logout"/>"><s:message code="header.link.logout" />, ${ userName }</a></li>
				</c:if>
				<li>|</li>
				<li><a href="?lang=en">eng</a></li>
				<li><a href="?lang=uk">rus</a></li>
			</ul>
		</div>
	</div>
</div>