package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.PageResultData;
import cc.coopersoft.restaurant.hr.repository.EmployeeBusinessRepository;
import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * Created by cooper on 7/30/16.
 */
@Named
@RequestScoped
public class EmployeeOperationList {

    private static final int PAGE_SIZE = 10;

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private EmployeeBusinessRepository employeeBusinessRepository;

    @Inject @Param(name = "firstResult")
    private Integer firstResult;

    @Inject @Param(name = "filterType")
    private Business.Type filterType;

    @Inject @Param(name = "dateFrom")
    private Date dateFrom;

    @Inject @Param(name = "dateTo")
    private Date dateTo;

    private PageResultData<EmployeeAction> resultData;

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
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getFirstResult() {
        if (firstResult == null){
            return 0;
        }
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    protected PageResultData<EmployeeAction> findResultData() {
        return new PageResultData<EmployeeAction>(employeeBusinessRepository.findActionData(employeeHome.getInstance().getId(),getDateFrom(),getDateFrom() != null,getDateTo(),getDateTo() != null,getFilterType(),getFilterType() != null,getFirstResult(),PAGE_SIZE),getFirstResult(),
                employeeBusinessRepository.findActionCount(employeeHome.getInstance().getId(),getDateFrom(),getDateFrom() != null,getDateTo(),getDateTo() != null,getFilterType(),getFilterType() != null),PAGE_SIZE);
    }

    public PageResultData<EmployeeAction> getResultData() {
        if (resultData == null){
            resultData = findResultData();
            this.firstResult = resultData.getFirstResult();
        }
        return resultData;
    }
}
