<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/rheapocadapterflushqueues/flushQueues.form" />

<form:form id="flush" modelAttribute="cleanup" method="post">
	<c:choose>
		<c:when test="${cleanup.status == null}">
			<p>Click the button below to flush the adapter module queues <br/> of all messages referring 
			to patients that are no longer in the system <br/> (i.e. those that were removed from the 
			ubudehe cleanup script).</p>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${cleanup.status == true}">
					<p>${cleanup.results.countArchive} of ${cleanup.results.totalArchive} messages flushed from the Archive Queue.</p>
					<br/>
					<p>${cleanup.results.countProcessing} of ${cleanup.results.totalProcessing} messages flushed from the Processing Queue.</p>
					<br/>
					<p>${cleanup.results.countError} of ${cleanup.results.totalError} messages flushed from the Error Queue.</p>
				</c:when>
				<c:otherwise>
					<p>Error flushing the queues. Check the logs for more info.</p>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<p><input type="submit" value="Flush Queues"></p>
</form:form>


<%@ include file="/WEB-INF/template/footer.jsp"%>