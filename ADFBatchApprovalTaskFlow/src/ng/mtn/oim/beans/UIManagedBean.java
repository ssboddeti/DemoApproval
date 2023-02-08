package ng.mtn.oim.beans;
import java.io.Serializable;

import java.util.logging.Logger;
import java.util.logging.Level;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.bpel.services.datacontrol.data.DataObject;

public class UIManagedBean implements Serializable {
    @SuppressWarnings("compatibility:-943658708107717110")
    private static final long serialVersionUID = 1088092188408981217L;
    private static final Logger log = Logger.getLogger(UIManagedBean.class.getName());

    public UIManagedBean() {
        super();
        System.out.println("*****************UIManagedBean is initalized********");
    }

    public void filterTheViewObject() {
        log.warning("filterTheViewObject started");
        try {
            System.out.println("got Batch ID ****"+getValueAttributeFromHumanTask("BatchID"));
       } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getValueAttributeFromHumanTask(String attributeName){
    String response = "";
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    AttributeBinding attr = (AttributeBinding)bindings.getControlBinding(attributeName);
    DataObject data = null;
    if(attr != null){
    data = (DataObject)attr.getInputValue();
    }
    if(data != null){
    response = data.getValue();
    }
    return response;
    }
}
