package net.sourceforge.fenixedu.presentationTier.docs.academicAdministrativeOffice;

import java.util.Locale;

import net.sourceforge.fenixedu.domain.Degree;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.degree.DegreeType;
import net.sourceforge.fenixedu.domain.degreeStructure.CycleType;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.organizationalStructure.UniversityUnit;
import net.sourceforge.fenixedu.domain.person.Gender;
import net.sourceforge.fenixedu.domain.phd.serviceRequests.documentRequests.PhdRegistryDiplomaRequest;
import net.sourceforge.fenixedu.domain.serviceRequests.IRegistryDiplomaRequest;
import net.sourceforge.fenixedu.domain.serviceRequests.documentRequests.IDocumentRequest;
import net.sourceforge.fenixedu.domain.serviceRequests.documentRequests.RegistryDiplomaRequest;
import net.sourceforge.fenixedu.util.StringUtils;

import org.joda.time.LocalDate;

public class RegistryDiploma extends AdministrativeOfficeDocument {
    private static final long serialVersionUID = 7788392282506503345L;

    protected RegistryDiploma(final IDocumentRequest documentRequest) {
	super(documentRequest);
    }

    @Override
    protected IRegistryDiplomaRequest getDocumentRequest() {
	return (IRegistryDiplomaRequest) super.getDocumentRequest();
    }

    @Override
    protected void fillReport() {
	super.fillReport();
	IRegistryDiplomaRequest request = (IRegistryDiplomaRequest) getDocumentRequest();
	Person person = request.getPerson();
	addParameter("code", request.getRegistryCode().getCode());

	final UniversityUnit university = UniversityUnit.getInstitutionsUniversityUnit();
	addParameter("institution", RootDomainObject.getInstance().getInstitutionUnit().getName());
	addParameter("university", university.getName());
	addParameter("principal", university.getInstitutionsUniversityPrincipal().getValidatedName());

	addParameter("studentName", person.getValidatedName());
	addParameter("idHolder", person.getGender() == Gender.MALE ? "portador" : "portadora");
	addParameter("idDocType", getEnumerationBundle().getString(person.getIdDocumentType().getName()));
	addParameter("idNumber", person.getDocumentIdNumber());
	addParameter("parishOfBirth", person.getParishOfBirth());

	addParameter("conclusionDay", verboseDate(request.getConclusionDate()));
	addParameter("graduateTitle", request.getGraduateTitle(getLocale()));

	addParameter("finalAverage", getDocumentRequest().getFinalAverage(getLocale()));
	addParameter("finalAverageQualified",
		getResourceBundle().getString(getDocumentRequest().getQualifiedAverageGrade(getLocale())));
	addParameter("date", new LocalDate().toString("dd 'de' MMMM 'de' yyyy", new Locale("pt")));
    }

    @Override
    protected String getDegreeDescription() {

	if (getDocumentRequest().isRequestForRegistration()) {
	    final StringBuilder res = new StringBuilder();
	    RegistryDiplomaRequest request = (RegistryDiplomaRequest) getDocumentRequest();
	    CycleType cycle = request.getRequestedCycle();
	    Degree degree = request.getDegree();
	    final DegreeType degreeType = request.getDegreeType();
	    if (degreeType.hasAnyCycleTypes()) {
		res.append(cycle.getDescription(getLocale()));
		res.append(StringUtils.SINGLE_SPACE).append(getResourceBundle().getString("label.of.the.male"))
			.append(StringUtils.SINGLE_SPACE);
	    }

	    if (!degree.isEmpty()) {
		res.append(degreeType.getPrefix(getLocale()));
		res.append(degreeType.getFilteredName(getLocale()));
		res.append(StringUtils.SINGLE_SPACE).append(getResourceBundle().getString("label.in"))
			.append(StringUtils.SINGLE_SPACE);
	    }

	    res.append(degree.getFilteredName(request.getConclusionYear()));
	    return res.toString();
	} else if (getDocumentRequest().isRequestForPhd()) {
	    PhdRegistryDiplomaRequest request = (PhdRegistryDiplomaRequest) getDocumentRequest();
	    return request.getPhdIndividualProgramProcess().getPhdProgram().getName().getContent();
	}
	
	throw new DomainException("docs.academicAdministrativeOffice.RegistryDiploma.degreeDescription.invalid.request");
    }

    private String verboseDate(LocalDate date) {
	return "dia " + getEnumerationBundle().getString(String.valueOf(date.getDayOfMonth())) + " do m�s de "
		+ date.toString("MMMM", new Locale("pt")) + " de "
		+ getEnumerationBundle().getString(String.valueOf(date.getYear()));
    }
}
