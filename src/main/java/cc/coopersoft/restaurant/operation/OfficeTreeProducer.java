package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EnumHelper;
import cc.coopersoft.restaurant.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import net.bootsfaces.component.tree.model.DefaultNodeImpl;
import net.bootsfaces.component.tree.model.Node;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 6/20/16.
 */
public class OfficeTreeProducer implements java.io.Serializable{

    public class OfficeTypeNodeImpl extends DefaultNodeImpl{

        public OfficeTypeNodeImpl(Office.Type type) {
            super(enumHelper.getLabel(type), type.getIcon(),true);
            setData(type.name());
        }

        public Office.Type getType(){
           return Office.Type.valueOf(getData());
        }
    }

    @Inject
    private OfficeRepository officeRepository;

    @Inject
    private EnumHelper enumHelper;

    @Produces
    @Named
    @SessionScoped
    public Node getOfficeTree(){
        Node result = new DefaultNodeImpl("root");
        List<Office.Type> types = new ArrayList<Office.Type>(EnumSet.allOf(Office.Type.class));
        Collections.sort(types);
        for(Office.Type type: types){


            List<Office> offices = officeRepository.findByCategoryVaild(type);
            if (!offices.isEmpty()) {
                Node typeNode = new OfficeTypeNodeImpl(type);
                for (Office office: offices){
                    typeNode.getChilds().add(new DefaultNodeImpl(office.getName(),"asterisk",office.getId()));
                }
                result.getChilds().add(typeNode);
            }
        }
        return result;

    }

}
