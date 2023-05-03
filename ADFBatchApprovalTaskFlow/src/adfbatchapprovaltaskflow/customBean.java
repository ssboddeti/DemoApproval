package adfbatchapprovaltaskflow;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

import oracle.bpel.services.workflow.worklist.adf.InvokeActionBean;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class customBean extends InvokeActionBean {
    private static ADFLogger logger = ADFLogger.createADFLogger(customBean.class);
    private String operationBinding;

    public customBean() {
        System.out.println("customBean is created");
    }

    public void validateServerListener(ClientEvent clientEvent) {
        // Add event code here...
        System.out.println("bean is working need to add messages");
        // get client id from event
        Object obj = clientEvent.getParameters().get("fcid");
        String clientId = "";
        if (obj != null) {
            clientId = obj.toString();
        }
        // get field value from event
        String val = "";
        Object obj2 = clientEvent.getParameters().get("fvalue");
        if (obj2 != null) {
            val = obj2.toString();
        }
        logger.warning("client id =" + clientId + " value=" + val);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "The request has been carried out", "The request has been " + val + " you can close the tab");
        facesContext.addMessage(null, msg);
        // javascript to set focus to component identified by it's clientId
        //String script = "var t=document.getElementById('" + clientId + "::content');t.focus();";
        //writeJavaScriptToClient(script);
    }

    //generic, reusable helper method to call JavaScript on a client
    private void writeJavaScriptToClient(String script) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = null;
        erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        erks.addScript(fctx, script);
    }

    public String invokeActionWithMessage() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        FacesMessage msg =
//            new FacesMessage(FacesMessage.SEVERITY_INFO, "The request has been carried out",
//                             "The request has been " + operationBinding + " you can close the tab");
//        facesContext.addMessage(null, msg);
        return invokeOperation();
    }


    public void setOperationOverride(ActionEvent actionEvent) {
        System.out.println("setOperationOverride " + actionEvent);
        try {
            operationBinding = (String) actionEvent.getComponent()
                                                   .getAttributes()
                                                   .get("DC_OPERATION_BINDING");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (operationBinding != null) {
            operationBinding = operationBinding.replace("bindings.", "");
        }
        System.out.println("operationBinding " + operationBinding);
        setOperation(actionEvent);
        System.out.println("invokeActionWithMessage " + operationBinding);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "The request has been carried out",
                             "The request has been " + operationBinding + " you can close the tab");
        facesContext.addMessage(null, msg);
        facesContext.renderResponse();
    }
}
