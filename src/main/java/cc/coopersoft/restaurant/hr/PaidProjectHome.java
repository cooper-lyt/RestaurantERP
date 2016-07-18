package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.hr.repository.PaidProjectRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.system.DictionaryProducer;
import cc.coopersoft.system.model.*;
import cc.coopersoft.system.model.Dictionary;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/4/16.
 */
@Named
@ConversationScoped
public class PaidProjectHome  extends EntityHome<PaidProject,String>{


    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private PaidProjectRepository paidProjectRepository;

    @Inject
    private DictionaryProducer dictionaryProducer;

    @Inject
    private FacesContext facesContext;


    protected PaidProject createInstance() {
        return new PaidProject(UUID.randomUUID().toString().replace("-",""),new Date());
    }

    protected EntityRepository<PaidProject, String> getEntityRepository() {
        return paidProjectRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private List<Map.Entry<String,List<PaidItem>>> paidItems;

    private List<PaidContentItem> paidContentItems;

    @Override
    protected void initInstance(){
        super.initInstance();
        paidItems = null;
        paidContentItems = null;
    }


    public List<PaidContentItem> getPaidContentItems(){
        if (paidContentItems == null){
            if (isIdDefined() && getInstance().getOfficeType().isProduce()){
                Map<Res,PaidContentItem> exists = new HashMap<Res, PaidContentItem>();
                for(PaidContentItem item: getInstance().getPaidContentItems()){
                    exists.put(item.getRes(),item);
                }

                paidContentItems = new ArrayList<PaidContentItem>();
                for(Res res: getInstance().getOfficeType().getProducts()){
                    if (res.isEnable()) {
                        PaidContentItem item = exists.get(res);
                        if (item == null) {
                            item = new PaidContentItem(UUID.randomUUID().toString().replace("-", ""), res, getInstance(), BigDecimal.ZERO);
                            getInstance().getPaidContentItems().add(item);
                        }
                        paidContentItems.add(item);
                    }
                }

                Collections.sort(paidContentItems, new Comparator<PaidContentItem>() {
                    public int compare(PaidContentItem o1, PaidContentItem o2) {
                        return o1.getRes().compareTo(o2.getRes());
                    }
                });
            }else{
                paidContentItems = new ArrayList<PaidContentItem>(0);
            }
        }
        return paidContentItems;
    }

    public List<Map.Entry<String,List<PaidItem>>> getPaidItems() {
        if (paidItems == null){
            Map<String,List<PaidItem>> itemMap = new HashMap<String, List<PaidItem>>();
            List<String> categories = paidProjectRepository.findCategoryByProject(getInstance().getId());
            for(String category: categories) {
                for (Job job : getInstance().getOfficeType().getJobs()) {
                    for (cc.coopersoft.system.model.Dictionary dictionary : dictionaryProducer.getDictionaries("hr.level")) {
                        List<PaidItem> pis = itemMap.get(category);
                        if (pis == null){
                            pis = new ArrayList<PaidItem>();
                            itemMap.put(category,pis);
                        }
                        pis.add(getAndCreatePaidItem(category,job,dictionary.getId()));
                    }
                }
            }
            paidItems = new ArrayList<Map.Entry<String,List<PaidItem>>>(itemMap.entrySet());

            Collections.sort(paidItems, new Comparator<Map.Entry<String, List<PaidItem>>>() {
                public int compare(Map.Entry<String, List<PaidItem>> o1, Map.Entry<String, List<PaidItem>> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });

        }
        return paidItems;
    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("paidProjectId"));
    }

    private String selectCategory;

    public String getSelectCategory() {
        return selectCategory;
    }

    public void setSelectCategory(String selectCategory) {
        this.selectCategory = selectCategory;
    }

    public void addCategory(){
        for(Job job : getInstance().getOfficeType().getJobs()){
            for (cc.coopersoft.system.model.Dictionary dictionary : dictionaryProducer.getDictionaries("hr.level")) {
                getAndCreatePaidItem(selectCategory,job,dictionary.getId());
            }
        }
        paidItems = null;
        saveOrUpdate();
    }

    public List<cc.coopersoft.system.model.Dictionary> getAllowCategories(){
        Map<String, cc.coopersoft.system.model.Dictionary> resultMap =  new HashMap<String, Dictionary>();
        for(Dictionary dictionary: dictionaryProducer.getDictionaries("hr.paidCategory")){
            resultMap.put(dictionary.getId(),dictionary);
        }
        for(String categoryId:  paidProjectRepository.findCategoryByProject(getInstance().getId())){
            resultMap.remove(categoryId);
        }

        List<cc.coopersoft.system.model.Dictionary> result = new ArrayList<Dictionary>(resultMap.values());

        Collections.sort(result);
        return result;
    }


    private PaidItem getAndCreatePaidItem(String category, Job job, String level){
        logger.config("category:" + category + "|job:" + job + "|level:" + level);
        for(PaidItem item: getInstance().getPaidItems()){
            if (item.getCategory().equals(category) && item.getJob().equals(job) && item.getLevel().equals(level)){
                return item;
            }
        }
        PaidItem result = new PaidItem(UUID.randomUUID().toString().replace("-",""),category,level,job,getInstance(), BigDecimal.ZERO);
        getInstance().getPaidItems().add(result);
        return result;
    }



}
