/*
 * Created on 1:42:45 PM,Mar 11, 2005
 *
 * Author: Goncalo Luiz (goncalo@ist.utl.pt)
 * 
 */

package net.sourceforge.fenixedu.presentationTier.Action.externalServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sourceforge.fenixedu._development.PropertiesManager;
import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.applicationTier.Filtro.exception.FenixFilterException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.externalServices.ReadStudentExternalInformation;
import net.sourceforge.fenixedu.applicationTier.utils.MockUserView;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.Role;
import net.sourceforge.fenixedu.domain.User;
import net.sourceforge.fenixedu.presentationTier.Action.BaseAuthenticationAction;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixAction;
import net.sourceforge.fenixedu.presentationTier.Action.exceptions.FenixActionException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.security.UserView;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

import com.thoughtworks.xstream.XStream;

/**
 * @author <a href="mailto:goncalo@ist.utl.pt">Goncalo Luiz </a>
 * 
 *         Created at 1:42:45 PM, Mar 11, 2005
 */
@Mapping(module = "external", path = "/infoStudent", scope = "request")
public class StudentInfoByUsername extends FenixAction {

    public class State {
        String studentUsername;

        String studentPassword;

        String xslt;

        String externalAppPassword;
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws FenixActionException {

        State state = new State();
        this.readRequestInformation(request, state);

        String result = new String();
        try {
            final String requestURL = request.getRequestURL().toString();
            final String remoteHostName = BaseAuthenticationAction.getRemoteHostName(request);
            authenticate(state.studentUsername, state.studentPassword, requestURL, remoteHostName, state.externalAppPassword);
            Collection students = this.readInformation(state.studentUsername);
            result = this.buildInfo(students);
            result = this.transformResult(result, state);
            if (result.equals("")) {
                result = "-1";
            }
        } catch (Throwable e) {
            e.printStackTrace();
            StringBuilder buffer = new StringBuilder();
            buffer.append("<error><cause>");
            buffer.append(e.getMessage());
            buffer.append("</cause></error>");
            result = buffer.toString();
        }

        try {
            sendAnswer(response, result);
        } catch (IOException ex) {
            throw new FenixActionException(ex);
        }

        return null;
    }

    private String transformResult(String result, State state) {
        String resultString = result;
        try {
            InputStream xsltStream = this.getClass().getResourceAsStream(this.getStyleFilename(state));
            StringWriter sw = new StringWriter();
            StringReader sr = new StringReader(result);

            Result transformationResult = new StreamResult(sw);
            Source source = new StreamSource(sr);
            javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer trans =
                    transFact.newTransformer(new javax.xml.transform.stream.StreamSource(xsltStream));
            trans.setOutputProperty("encoding", PropertiesManager.DEFAULT_CHARSET);
            trans.transform(source, transformationResult);
            resultString = sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // let us proceed with default layout
            resultString = result;
        }

        return resultString;
    }

    private String getStyleFilename(State state) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("/xslt/external/studentInfoByUsername/").append(state.xslt).append(".xslt");
        return buffer.toString();
    }

    /**
     * @param studentUsername2
     * @return
     * @throws FenixServiceException
     * @throws FenixFilterException
     */
    private Collection readInformation(String username) throws FenixFilterException, FenixServiceException {

        return ReadStudentExternalInformation.run(username);
    }

    private String buildInfo(Collection students) {
        XStream xstream = new XStream();
        return xstream.toXML(students);
    }

    private void readRequestInformation(HttpServletRequest request, State state) {
        state.studentUsername = request.getParameter("username");
        state.studentPassword = request.getParameter("password");
        state.xslt = request.getParameter("style");
        state.externalAppPassword = request.getParameter("externalAppPassword");
    }

    private void authenticate(String username, String password, String requestURL, String remoteHostName,
            String externalAppPassword) throws FenixServiceException, FenixFilterException {
        IUserView userView = null;
        if (externalAppPassword != null
                && externalAppPassword.equals(PropertiesManager
                        .getProperty("externalServices.StudentInfoByUsername.externalAppPassword"))) {
            userView = new MockUserView(username, new ArrayList<Role>(), Person.readPersonByUsername(username));
        } else {
            final User user = User.readUserByUserUId(username);
            if (user != null && password != null && password.equals(user.getPerson().getStudent().getExportInformationPassword())) {
                userView = new MockUserView(username, new ArrayList<Role>(), Person.readPersonByUsername(username));
            } else {
                throw new Error("bad.authentication");
            }
        }
        UserView.setUser(userView);
    }

    private void sendAnswer(HttpServletResponse response, String result) throws IOException {
        ServletOutputStream writer = response.getOutputStream();
        response.setContentType("text/plain");
        writer.print(result);
        writer.flush();
        response.flushBuffer();
    }
}