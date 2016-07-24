package cc.coopersoft.restaurant;

import cc.coopersoft.common.I18n;
import cc.coopersoft.common.PageResultData;
import cc.coopersoft.common.util.ConditionAdapter;
import cc.coopersoft.restaurant.model.Business;
import org.omnifaces.cdi.Param;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by cooper on 7/24/16.
 */
public abstract class BusinessList implements java.io.Serializable{

    @Inject
    private I18n i18n;

    @Inject @Param(name = "condition")
    private String condition;

    @Inject @Param(name = "filterType")
    private Business.Type filterType;

    @Inject @Param(name = "dateFrom")
    private Date dateFrom;

    @Inject @Param(name = "dateTo")
    private Date dateTo;

    @Inject @Param(name = "firstResult")
    private Integer firstResult;

    private PageResultData<Business> resultData;

    public ConditionAdapter getConditionAdapter() {
        return ConditionAdapter.instance(getCondition());
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Business.Type getFilterType() {
        return filterType;
    }

    public void setFilterType(Business.Type filterType) {
        this.filterType = filterType;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = i18n.getDayBeginTime(dateFrom);
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = i18n.getDayEndTime(dateTo);
    }

    public int getFirstResult() {
        if (firstResult == null){
            return 0;
        }
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    protected abstract PageResultData<Business> findResultData();

    public PageResultData<Business> getResultData() {
        if (resultData == null){
            resultData = findResultData();
            this.firstResult = resultData.getFirstResult();
        }
        return resultData;
    }
}
