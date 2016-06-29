package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.model.Job;
import cc.coopersoft.restaurant.model.OfficeType;
import cc.coopersoft.restaurant.operation.repository.OfficeTypeRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.omnifaces.cdi.Param;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by cooper on 6/24/16.
 */
@Named
@RequestScoped
public class OfficeTypeHome extends EntityHome<OfficeType,String> {

    @Inject
    private OfficeTypeRepository officeTypeRepository;

    @Inject
    private FacesContext facesContext;

    private String selectJobId;

    public String getSelectJobId() {
        return selectJobId;
    }

    public void setSelectJobId(String selectJobId) {
        this.selectJobId = selectJobId;
    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("officeTypeId"));
    }


    @Inject @Default
    private EntityManager entityManager;

    protected OfficeType createInstance() {
        return new OfficeType(UUID.randomUUID().toString().replace("-",""),new Date(),true);
    }

    protected EntityRepository<OfficeType, String> getEntityRepository() {
        return officeTypeRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private List<Job> jobs;

    public List<Job> getJobs() {
        if ( jobs == null || clearDirty()){

            jobs = new ArrayList<Job>(getInstance().getJobs());
            Collections.sort(jobs);
        }
        return jobs;
    }

    private String newJobName;

    public String getNewJobName() {
        return newJobName;
    }

    public void setNewJobName(String newJobName) {
        this.newJobName = newJobName;
    }

    @Override
    @Transactional
    public void saveOrUpdate(){
        super.saveOrUpdate();
        setId(getInstance().getId());
    }

    private void switchJobPri(Job a, Job b){
        int pri = a.getPri();
        a.setPri(b.getPri());
        b.setPri(pri);
        super.saveOrUpdate();
        Collections.sort(jobs);
    }

    @Transactional
    public void upJob(){
        Job befor = null;
        for(Job job: getJobs()){
            if (befor != null && selectJobId.equals(job.getId())){
                switchJobPri(befor,job);
                return;
            }else{
                befor = job;
            }
        }
    }

    @Transactional
    public void downJob(){
        Job selectJob = null;
        for(Job job: getJobs()){
            if (selectJob == null){
                if (selectJobId.equals(job.getId())){
                    selectJob = job;
                }
            }else{
                switchJobPri(selectJob,job);
                return;
            }
        }
    }

    @Transactional
    public void addJob(){
        int maxPri = 1;
        for(Job job: getInstance().getJobs()){
            if (job.getPri() > maxPri){
                maxPri = job.getPri();
            }
        }
        getInstance().getJobs().add(new Job(UUID.randomUUID().toString().replace("-",""),newJobName,maxPri + 1,true,getInstance()));
        saveOrUpdate();
        jobs = null;
        newJobName = null;

    }
}
