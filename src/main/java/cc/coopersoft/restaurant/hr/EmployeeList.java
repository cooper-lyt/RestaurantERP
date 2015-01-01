package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.BatchData;
import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.operation.OfficeHome;
import net.bootsfaces.component.tree.event.TreeNodeCheckedEvent;
import net.bootsfaces.component.tree.event.TreeNodeEventListener;
import net.bootsfaces.component.tree.event.TreeNodeSelectionEvent;
import net.bootsfaces.component.tree.model.Node;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/20/16.
 */
@Named
@RequestScoped
public class EmployeeList implements TreeNodeEventListener, java.io.Serializable {

    @Inject
    private OfficeHome officeHome;

    @Inject
    private Logger logger;

    private List<BatchData<Employee>> employeeList;

    public List<BatchData<Employee>> getEmployeeList() {
        if (employeeList == null){
            if (officeHome.isIdDefined()){
                employeeList = BatchData.fillData(officeHome.getInstance().getEmployeeList());
            }else{
                employeeList = new ArrayList<BatchData<Employee>>(0);
            }
        }
        return employeeList;
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
