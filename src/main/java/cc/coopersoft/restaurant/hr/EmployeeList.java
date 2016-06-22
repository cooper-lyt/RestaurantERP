package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.BatchData;
import cc.coopersoft.common.util.ConditionAdapter;
import cc.coopersoft.common.util.DataHelper;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice;
import cc.coopersoft.restaurant.model.Employee;
import net.bootsfaces.component.tree.event.TreeNodeCheckedEvent;
import net.bootsfaces.component.tree.event.TreeNodeEventListener;
import net.bootsfaces.component.tree.event.TreeNodeSelectionEvent;
import net.bootsfaces.component.tree.model.Node;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/20/16.
 */
@Named
@ConversationScoped
public class EmployeeList implements TreeNodeEventListener, java.io.Serializable {


    private ConditionAdapter job = ConditionAdapter.instance(null);

    private ConditionAdapter condition = ConditionAdapter.instance(null);

    private String officeId;

    public String getJob() {
        return job.getCondition();
    }

    public void setJob(String job) {
        if (this.job.isDirty(job)) refresh();
        this.job = ConditionAdapter.instance(job);

    }

    public String getCondition() {
        return condition.getCondition();
    }

    public void setCondition(String condition) {
        if (this.condition.isDirty(condition)) refresh();
        this.condition = ConditionAdapter.instance(condition);
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        if (DataHelper.isDirty(officeId,this.officeId)) employees = null;
        this.officeId = officeId;
    }

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private EmployeeRepository employeeRepository;

    private List<EmployeeOffice> offices;

    public List<EmployeeOffice> getOffices() {
        initOffices();
        return offices;
    }

    protected void initOffices(){
        if (offices == null){
            offices = employeeRepository.searchEmployeeOffices(condition.getContains(),condition.getCondition(),condition.isEmpty(),job.getCondition(),job.isEmpty());

            if (!offices.isEmpty() && DataHelper.empty(officeId)){
                officeId = offices.get(0).getId();
            }
        }
    }

    private List<BatchData<Employee>> employees;

    public List<BatchData<Employee>> getEmployees() {
        initEmployees();
        return employees;
    }

    @PostConstruct
    public void initParam(){
        officeId = facesContext.getExternalContext().getRequestParameterMap().get("employeeOfficeId");
        setCondition(facesContext.getExternalContext().getRequestParameterMap().get("condition"));
        setJob(facesContext.getExternalContext().getRequestParameterMap().get("job"));
    }


    protected void initEmployees(){
        if (employees == null){
            if (DataHelper.empty(officeId)){
                initOffices();
            }
            if (!DataHelper.empty(officeId)) {
                employees = BatchData.fillData(employeeRepository.searchEmployee(condition.getContains(), condition.getCondition(), condition.isEmpty(), job.getCondition(), job.isEmpty(), officeId));
            }
        }
    }

    public void refresh(){
        officeId = null;
        offices = null;
        employees = null;
    }

    public void processValueChange(TreeNodeSelectionEvent treeNodeSelectionEvent) {
        Node node =treeNodeSelectionEvent.getNewSelectedNode();

    }

    public void processValueChecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {

    }

    public void processValueUnchecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {

    }

    public boolean isValueSelected(Node node) {

        return true;
    }

    public boolean isValueChecked(Node node) {
        return true;
    }
}
