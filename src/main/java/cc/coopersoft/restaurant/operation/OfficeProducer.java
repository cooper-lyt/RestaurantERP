package cc.coopersoft.restaurant.operation;

import cc.coopersoft.restaurant.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by cooper on 6/18/16.
 */
public class OfficeProducer {

    @Inject
    private OfficeRepository officeRepository;

    @Produces
    @Named("officeList")
    @RequestScoped
    public List<Office> getAllOffice(){
        return officeRepository.findAllVaild();
    }

}
