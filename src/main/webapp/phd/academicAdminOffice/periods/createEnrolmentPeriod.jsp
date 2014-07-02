<%--

    Copyright © 2002 Instituto Superior Técnico

    This file is part of FenixEdu Core.

    FenixEdu Core is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Core is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<%@page import="net.sourceforge.fenixedu.domain.phd.ManageEnrolmentsBean"%>
<%@page import="net.sourceforge.fenixedu.presentationTier.Action.phd.coordinator.providers.PhdDegreeCurricularPlansProvider"%>

<html:xhtml/>

<%-- ### Title #### --%>
<h2><bean:message key="label.phd.create.enrolment.period" bundle="PHD_RESOURCES" /></h2>
<%-- ### End of Title ### --%>

<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>
<html:link action="/managePhdEnrolmentPeriods.do?method=manageEnrolmentPeriods">
	<bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>
<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>

<%--  ### Error Messages  ### --%>
<jsp:include page="/phd/errorsAndMessages.jsp" />
<%--  ### End of Error Messages  ### --%>


<fr:edit id="createBean" name="createBean" 
		 action="/managePhdEnrolmentPeriods.do?method=createEnrolmentPeriod">

	<fr:schema bundle="PHD_RESOURCES" type="<%= ManageEnrolmentsBean.class.getName() %>">

		<fr:slot name="startDate" required="true" />
		<fr:slot name="endDate" required="true" />

		<fr:slot name="degreeCurricularPlans" layout="option-select" required="true">
			<fr:property name="classes" value="nobullet noindent" />
		
			<fr:property name="providerClass" value="<%= PhdDegreeCurricularPlansProvider.class.getName() %>" />

			<fr:property name="eachLayout" value="values" />
			<fr:property name="eachSchema" value="DegreeCurricularPlan.presentationName" />

			<fr:property name="sortBy" value="presentationName" />
			<fr:property name="selectAllShown" value="true" />
		</fr:slot>
	</fr:schema>

	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
		<fr:property name="columnClasses" value=",,tdclear tderror1" />
		<fr:property name="requiredMarkShown" value="true" />
	</fr:layout>
	
	<fr:destination name="invalid" path="/managePhdEnrolmentPeriods.do?method=createEnrolmentPeriodInvalid"/>
	<fr:destination name="cancel" path="/managePhdEnrolmentPeriods.do?method=manageEnrolmentPeriods" />
	
</fr:edit>