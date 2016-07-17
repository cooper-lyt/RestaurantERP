package cc.coopersoft.restaurant.operation;

import cc.coopersoft.restaurant.model.Job;
import cc.coopersoft.restaurant.model.Res;
import cc.coopersoft.restaurant.operation.repository.OfficeTypeRepository;
import cc.coopersoft.restaurant.res.ResHome;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@RequestScoped
public class OfficeTypeOperation implements java.io.Serializable{

    @Inject
    private OfficeTypeHome officeTypeHome;

    @Inject
    private OfficeTypeRepository officeTypeRepository;

    @Inject
    private ResHome resHome;

    private List<Job> jobs;

    public List<Job> getJobs() {
        if ( jobs == null){
            jobs = new ArrayList<Job>(officeTypeHome.getInstance().getJobs());
            Collections.sort(jobs);
        }
        return jobs;
    }

    private String selectJobId;

    public String getSelectJobId() {
        return selectJobId;
    }

    public void setSelectJobId(String selectJobId) {
        this.selectJobId = selectJobId;
    }



    private String newJobName;

    public String getNewJobName() {
        return newJobName;
    }

    public void setNewJobName(String newJobName) {
        this.newJobName = newJobName;
    }


    private void switchJobPri(Job a, Job b){
        int pri = a.getPri();
        a.setPri(b.getPri());
        b.setPri(pri);
        officeTypeHome.saveOrUpdate();
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
        for(Job job: officeTypeHome.getInstance().getJobs()){
            if (job.getPri() > maxPri){
                maxPri = job.getPri();
            }
        }
        officeTypeHome.getInstance().getJobs().add(new Job(UUID.randomUUID().toString().replace("-",""),newJobName,maxPri + 1,true,officeTypeHome.getInstance()));
        officeTypeHome.saveOrUpdate();
        jobs = null;
        newJobName = null;
    }


    private List<Res> produces;


    public List<Res> getProduces() {
        if (produces == null){
            produces = new ArrayList<Res>(officeTypeHome.getInstance().getProducts());
            Collections.sort(produces);
        }
        return produces;
    }


    @Transactional
    public void addProduct(){
        officeTypeHome.getInstance().getProducts().add(resHome.getInstance());
        produces =  null;
        if (canAddResList != null){
            canAddResList.remove(resHome.getInstance());
        }

        officeTypeHome.saveOrUpdate();
    }

    @Transactional
    public void removeProduct(){
        officeTypeHome.getInstance().getProducts().remove(resHome.getInstance());
        produces = null;
        canAddResList = null;
        officeTypeHome.saveOrUpdate();

    }

    private List<Res> canAddResList;

    public List<Res> getCanAddResList(){
        if (canAddResList == null){
            List<String> haveIds = new ArrayList<String>(getProduces().size());
            for(Res produce: getProduces()){
                haveIds.add(produce.getId());
            }
            if (haveIds.isEmpty()){
                canAddResList = officeTypeRepository.getAllProduceRes();
            }else
                canAddResList = officeTypeRepository.getCanSelectProduceRes(haveIds);
        }
        return canAddResList;

    }

}
