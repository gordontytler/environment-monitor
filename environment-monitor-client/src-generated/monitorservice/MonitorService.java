
package monitorservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "MonitorService", targetNamespace = "http://MonitorService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MonitorService {


    /**
     * 
     * @param host
     * @param environmentName
     * @param hostName
     * @return
     *     returns monitorservice.LogonResult
     */
    @WebMethod
    @WebResult(name = "loginResult", targetNamespace = "")
    @RequestWrapper(localName = "logon", targetNamespace = "http://MonitorService", className = "monitorservice.Logon")
    @ResponseWrapper(localName = "logonResponse", targetNamespace = "http://MonitorService", className = "monitorservice.LogonResponse")
    public LogonResult logon(
        @WebParam(name = "host", targetNamespace = "")
        String host,
        @WebParam(name = "hostName", targetNamespace = "")
        String hostName,
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName);

    /**
     * 
     * @param chunkNumber
     * @param sessionId
     * @return
     *     returns monitorservice.OutputChunkResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOutputChunk", targetNamespace = "http://MonitorService", className = "monitorservice.GetOutputChunk")
    @ResponseWrapper(localName = "getOutputChunkResponse", targetNamespace = "http://MonitorService", className = "monitorservice.GetOutputChunkResponse")
    public OutputChunkResult getOutputChunk(
        @WebParam(name = "sessionId", targetNamespace = "")
        String sessionId,
        @WebParam(name = "chunkNumber", targetNamespace = "")
        int chunkNumber);

    /**
     * 
     * @param sessionId
     * @param command
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "executeCommand", targetNamespace = "http://MonitorService", className = "monitorservice.ExecuteCommand")
    @ResponseWrapper(localName = "executeCommandResponse", targetNamespace = "http://MonitorService", className = "monitorservice.ExecuteCommandResponse")
    public CommandResult executeCommand(
        @WebParam(name = "command", targetNamespace = "")
        String command,
        @WebParam(name = "sessionId", targetNamespace = "")
        String sessionId);

    /**
     * 
     * @param environmentName
     * @param outputHistoryTimeStamp
     * @return
     *     returns monitorservice.EnvironmentView
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEnvironmentView", targetNamespace = "http://MonitorService", className = "monitorservice.GetEnvironmentView")
    @ResponseWrapper(localName = "getEnvironmentViewResponse", targetNamespace = "http://MonitorService", className = "monitorservice.GetEnvironmentViewResponse")
    public EnvironmentView getEnvironmentView(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName,
        @WebParam(name = "outputHistoryTimeStamp", targetNamespace = "")
        long outputHistoryTimeStamp);

    /**
     * 
     * @param environmentName
     * @param environmentViewRow
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "executeAction", targetNamespace = "http://MonitorService", className = "monitorservice.ExecuteAction")
    @ResponseWrapper(localName = "executeActionResponse", targetNamespace = "http://MonitorService", className = "monitorservice.ExecuteActionResponse")
    public CommandResult executeAction(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName,
        @WebParam(name = "environmentViewRow", targetNamespace = "")
        EnvironmentViewRow environmentViewRow);

    /**
     * 
     * @param sessionId
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "killRunningCommand", targetNamespace = "http://MonitorService", className = "monitorservice.KillRunningCommand")
    @ResponseWrapper(localName = "killRunningCommandResponse", targetNamespace = "http://MonitorService", className = "monitorservice.KillRunningCommandResponse")
    public CommandResult killRunningCommand(
        @WebParam(name = "sessionId", targetNamespace = "")
        String sessionId);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(name = "environmentNames", targetNamespace = "")
    @RequestWrapper(localName = "getEnvironmentNames", targetNamespace = "http://MonitorService", className = "monitorservice.GetEnvironmentNames")
    @ResponseWrapper(localName = "getEnvironmentNamesResponse", targetNamespace = "http://MonitorService", className = "monitorservice.GetEnvironmentNamesResponse")
    public List<String> getEnvironmentNames();

    /**
     * 
     * @param environmentName
     * @param serverName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "addServerResult", targetNamespace = "")
    @RequestWrapper(localName = "addServer", targetNamespace = "http://MonitorService", className = "monitorservice.AddServer")
    @ResponseWrapper(localName = "addServerResponse", targetNamespace = "http://MonitorService", className = "monitorservice.AddServerResponse")
    public CommandResult addServer(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName,
        @WebParam(name = "serverName", targetNamespace = "")
        String serverName);

    /**
     * 
     * @param index
     * @param environmentName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "deleteRowResult", targetNamespace = "")
    @RequestWrapper(localName = "deleteRow", targetNamespace = "http://MonitorService", className = "monitorservice.DeleteRow")
    @ResponseWrapper(localName = "deleteRowResponse", targetNamespace = "http://MonitorService", className = "monitorservice.DeleteRowResponse")
    public CommandResult deleteRow(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName,
        @WebParam(name = "index", targetNamespace = "")
        int index);

    /**
     * 
     * @param environmentName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "deleteEnvironmentResult", targetNamespace = "")
    @RequestWrapper(localName = "deleteEnvironment", targetNamespace = "http://MonitorService", className = "monitorservice.DeleteEnvironment")
    @ResponseWrapper(localName = "deleteEnvironmentResponse", targetNamespace = "http://MonitorService", className = "monitorservice.DeleteEnvironmentResponse")
    public CommandResult deleteEnvironment(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName);

    /**
     * 
     * @param environmentName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "saveEnvironmentResult", targetNamespace = "")
    @RequestWrapper(localName = "saveEnvironment", targetNamespace = "http://MonitorService", className = "monitorservice.SaveEnvironment")
    @ResponseWrapper(localName = "saveEnvironmentResponse", targetNamespace = "http://MonitorService", className = "monitorservice.SaveEnvironmentResponse")
    public CommandResult saveEnvironment(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName);

    /**
     * 
     * @param newName
     * @param oldName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "renameEnvironmentResult", targetNamespace = "")
    @RequestWrapper(localName = "renameEnvironment", targetNamespace = "http://MonitorService", className = "monitorservice.RenameEnvironment")
    @ResponseWrapper(localName = "renameEnvironmentResponse", targetNamespace = "http://MonitorService", className = "monitorservice.RenameEnvironmentResponse")
    public CommandResult renameEnvironment(
        @WebParam(name = "oldName", targetNamespace = "")
        String oldName,
        @WebParam(name = "newName", targetNamespace = "")
        String newName);

    /**
     * 
     * @param environmentName
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "restartOutputsResult", targetNamespace = "")
    @RequestWrapper(localName = "restartOutputs", targetNamespace = "http://MonitorService", className = "monitorservice.RestartOutputs")
    @ResponseWrapper(localName = "restartOutputsResponse", targetNamespace = "http://MonitorService", className = "monitorservice.RestartOutputsResponse")
    public CommandResult restartOutputs(
        @WebParam(name = "environmentName", targetNamespace = "")
        String environmentName);

    /**
     * 
     * @param outputName
     * @param sessionId
     * @param fileName
     * @param nameInEnvironmentView
     * @return
     *     returns monitorservice.CommandResult
     */
    @WebMethod
    @WebResult(name = "addApplicationResult", targetNamespace = "")
    @RequestWrapper(localName = "addApplication", targetNamespace = "http://MonitorService", className = "monitorservice.AddApplication")
    @ResponseWrapper(localName = "addApplicationResponse", targetNamespace = "http://MonitorService", className = "monitorservice.AddApplicationResponse")
    public CommandResult addApplication(
        @WebParam(name = "sessionId", targetNamespace = "")
        String sessionId,
        @WebParam(name = "nameInEnvironmentView", targetNamespace = "")
        String nameInEnvironmentView,
        @WebParam(name = "fileName", targetNamespace = "")
        String fileName,
        @WebParam(name = "outputName", targetNamespace = "")
        String outputName);

    /**
     * 
     * @param fileName
     * @param nameInEnvironmentView
     * @return
     *     returns monitorservice.Application
     */
    @WebMethod
    @WebResult(name = "application", targetNamespace = "")
    @RequestWrapper(localName = "loadApplicationByFileName", targetNamespace = "http://MonitorService", className = "monitorservice.LoadApplicationByFileName")
    @ResponseWrapper(localName = "loadApplicationByFileNameResponse", targetNamespace = "http://MonitorService", className = "monitorservice.LoadApplicationByFileNameResponse")
    public Application loadApplicationByFileName(
        @WebParam(name = "fileName", targetNamespace = "")
        String fileName,
        @WebParam(name = "nameInEnvironmentView", targetNamespace = "")
        String nameInEnvironmentView);

    /**
     * 
     * @param sessionId
     */
    @WebMethod
    @RequestWrapper(localName = "close", targetNamespace = "http://MonitorService", className = "monitorservice.Close")
    @ResponseWrapper(localName = "closeResponse", targetNamespace = "http://MonitorService", className = "monitorservice.CloseResponse")
    public void close(
        @WebParam(name = "sessionId", targetNamespace = "")
        String sessionId);

}
