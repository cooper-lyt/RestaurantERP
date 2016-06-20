package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.model.Office;
import cc.coopersoft.restaurant.operation.OfficeHome;
import cc.coopersoft.restaurant.operation.OfficeTreeProducer;
import net.bootsfaces.component.tree.event.TreeNodeCheckedEvent;
import net.bootsfaces.component.tree.event.TreeNodeEventListener;
import net.bootsfaces.component.tree.event.TreeNodeSelectionEvent;
import net.bootsfaces.component.tree.model.Node;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/20/16.
 */
@Named
public class EmployeeList implements TreeNodeEventListener, java.io.Serializable {

    @Inject
    private OfficeHome officeHome;

    @Inject
    private Logger logger;



    public void processValueChange(TreeNodeSelectionEvent treeNodeSelectionEvent) {
        Node node =treeNodeSelectionEvent.getNewSelectedNode();

        logger.config("dd" + node.getText());
        if (node instanceof OfficeTreeProducer.OfficeTypeNodeImpl){
            logger.config("1.select type node!");
        }else{
            logger.config("1.not select type node data:" + node.getData());
        }
    }

    public void processValueChecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {

    }

    public void processValueUnchecked(TreeNodeCheckedEvent treeNodeCheckedEvent) {

    }

    public boolean isValueSelected(Node node) {
        if (node instanceof OfficeTreeProducer.OfficeTypeNodeImpl){
            logger.config("select type node!");
        }else{
            logger.config("not select type node data:" + node.getData());
        }
        return true;
    }

    public boolean isValueChecked(Node node) {
        return true;
    }
}
