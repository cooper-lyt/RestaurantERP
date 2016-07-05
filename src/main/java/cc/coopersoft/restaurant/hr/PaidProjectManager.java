package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.hr.repository.PaidProjectRepository;
import cc.coopersoft.restaurant.model.PaidItem;
import cc.coopersoft.restaurant.model.PaidProject;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 7/4/16.
 */
@Named
public class PaidProjectManager {

    @Inject
    private PaidProjectHome paidProjectHome;

    @Inject
    private PaidProjectRepository paidProjectRepository;

    private List<PaidProject> paidProjects;

    public List<PaidProject> getPaidProjects() {
        if (paidProjects == null){
            paidProjects = paidProjectRepository.findAllOrderByBotimeAsc();
        }
        return paidProjects;
    }





}
