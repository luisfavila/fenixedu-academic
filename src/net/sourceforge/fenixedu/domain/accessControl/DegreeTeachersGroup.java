package net.sourceforge.fenixedu.domain.accessControl;

import java.util.Set;

import net.sourceforge.fenixedu.domain.CurricularCourse;
import net.sourceforge.fenixedu.domain.Degree;
import net.sourceforge.fenixedu.domain.DegreeCurricularPlan;
import net.sourceforge.fenixedu.domain.ExecutionCourse;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.Professorship;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

public class DegreeTeachersGroup extends DegreeGroup {

    /**
         * 
         */
    private static final long serialVersionUID = 8466471514890333054L;

    public DegreeTeachersGroup(Degree degree) {
	super(degree);
    }

    @Override
    public String getName() {
	return RenderUtils.getFormatedResourceString("GROUP_NAME_RESOURCES", "label.name." + getClass().getSimpleName(),
		getObject().getName());
    }

    @Override
    public Set<Person> getElements() {
	Set<Person> elements = super.buildSet();

	for (DegreeCurricularPlan degreeCurricularPlan : getDegree().getActiveDegreeCurricularPlans()) {
	    for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
		for (ExecutionCourse executionCourse : curricularCourse.getAssociatedExecutionCourses()) {
		    for (Professorship professorship : executionCourse.getProfessorships()) {
			elements.add(professorship.getPerson());
		    }
		}
	    }
	}

	return super.freezeSet(elements);
    }

    @Override
    public boolean isMember(Person person) {
	if (person != null && person.hasTeacher()) {
	    for (final Professorship professorship : person.getTeacher().getProfessorshipsSet()) {
		for (final CurricularCourse curricularCourse : professorship.getExecutionCourse()
			.getAssociatedCurricularCoursesSet()) {
		    if (curricularCourse.getDegree() == getDegree()) {
			return true;
		    }
		}
	    }
	}

	return false;

    }

    public static class Builder extends DegreeGroup.DegreeGroupBuilder {

	public Group build(Object[] arguments) {
	    return new DegreeTeachersGroup(getDegree(arguments));
	}

    }
}
