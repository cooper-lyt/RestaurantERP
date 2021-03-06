package cc.coopersoft.restaurant.operation;

import cc.coopersoft.restaurant.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 6/10/16.
 */
@Model
@RequestScoped
public class OfficeManager {


    @Inject
    private OfficeRepository officeRepository;

    @Inject @Param
    private Boolean showDestroy;

    @Inject @Param
    private String condition;


    public Boolean getShowDestroy() {
        return showDestroy;
    }

    public void setShowDestroy(Boolean showDestroy) {
        this.showDestroy = showDestroy;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Office.OfficeStatus> getAllOfficeStatusList(){
        return new ArrayList<Office.OfficeStatus>(EnumSet.of(Office.OfficeStatus.PREPARE,Office.OfficeStatus.OPEN, Office.OfficeStatus.CLOSE, Office.OfficeStatus.DESTROY));
    }

    public void refresh(){
        resultList = null;
    }

    private List<Office> resultList;

    public List<Office> getResultList(){
        if (resultList == null){
            resultList = new ArrayList<Office>();

            String sqlCondition = condition;

            if (showDestroy != null && showDestroy){
                if (sqlCondition == null || sqlCondition.trim().equals("")){
                    resultList = officeRepository.findAll();
                }else {
                    sqlCondition = "%" + condition.trim() + "%";
                    resultList = officeRepository.findByConditionAll(sqlCondition);
                }
            }else{
                if (sqlCondition == null || sqlCondition.trim().equals("")){
                    resultList = officeRepository.findAllVaild();
                }else{
                    sqlCondition = "%" + condition.trim() + "%";
                    resultList = officeRepository.findByCondition(sqlCondition);
                }

            }

        }
        return resultList;
    }
}
