<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="confirmDialogModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<span id="dialogHeader"></span>
				</h4>
			</div>

			<div class="modal-body">
				<h4 class="modal-title">
					<span id="dialogBody"></span>
				</h4>
			</div>

			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<s:message code="global.button.cancel" />
				</button>
				<button id="dialogConfirmButton" class="btn btn-danger" type="button">
					<s:message code="global.button.delete" />
				</button>
			</div>
		</div>
	</div>
</div>

<script>

</script>