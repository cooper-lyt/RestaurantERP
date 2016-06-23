package cc.coopersoft.restaurant.operation;

import cc.coopersoft.restaurant.model.OfficeType;
import cc.coopersoft.restaurant.operation.repository.OfficeTypeRepository;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by cooper on 6/24/16.
 */
@Named
@RequestScoped
public class OfficeTypeManager {

    @Inject
    private OfficeTypeRepository officeTypeRepository;

    private List<OfficeType> officeTypes;

    public List<OfficeType> getOfficeTypes() {
        if (officeTypes == null){
            officeTypes = officeTypeRepository.findAllOrderByBotimeAsc();
        }
        return officeTypes;
    }

    public void refresh(){
        officeTypes = null;
    }

    @Produces @Named
    public List<OfficeType> getOfficeTypeList(){
        return officeTypeRepository.findEnableTypes();
    }
}
