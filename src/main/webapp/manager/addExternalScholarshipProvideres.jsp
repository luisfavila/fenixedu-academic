<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html:xhtml/>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>


<logic:present role="MANAGER">

<em>Entidades Externas para Bolsas</em>
<h2>Adicionar Entidades</h2>

		<fr:form action="/externalScholarshipProvider.do?method=add">
		
		<fr:edit id="bean" name="bean">
			<fr:schema bundle="MANAGER_RESOURCES" type="net.sourceforge.fenixedu.presentationTier.Action.ExternalScholarshipProviderDA$ExternalScholarshipBean" >
				<fr:slot name="selected" layout="autoComplete" key="label.nif" validator="net.sourceforge.fenixedu.presentationTier.renderers.validators.RequiredAutoCompleteSelectionValidator">
					<fr:property name="size" value="50"/>
					<fr:property name="rawSlotName" value="selected"/>
					<fr:property name="labelField" value="name"/>
					<fr:property name="indicatorShown" value="true"/>		
					<fr:property name="serviceName" value="SearchPartyByNif"/>
					<fr:property name="serviceArgs" value="slot=name"/>
					<fr:property name="minChars" value="3"/>
					<fr:property name="format" value="${name}"/>
				</fr:slot>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle5 thright thlight"/>
				<fr:property name="columnClasses" value=",,tdclear tderror1"/>
			</fr:layout>
		</fr:edit>
		<html:submit><bean:message key="label.add" bundle="APPLICATION_RESOURCES"/></html:submit>
		</fr:form>
</logic:present>
