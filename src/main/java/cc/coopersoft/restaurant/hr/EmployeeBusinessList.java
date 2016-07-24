package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.PageResultData;
import cc.coopersoft.restaurant.BusinessList;
import cc.coopersoft.restaurant.hr.repository.EmployeeBusinessRepository;
import cc.coopersoft.restaurant.model.Business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by cooper on 7/24/16.
 */
@Named
@RequestScoped
public class EmployeeBusinessList extends BusinessList{

    private static final int PAGE_SIZE = 25;

    @Inject
    private EmployeeBusinessRepository employeeBusinessRepository;

    protected PageResultData<Business> findResultData() {


        return new PageResultData<Business>(employeeBusinessRepository.searchResultData(getConditionAdapter().getCondition(),getConditionAdapter().getContains(),getConditionAdapter().isEmpty(),getDateFrom(),getDateFrom() != null,getDateTo(),getDateTo() != null,getFilterType(),getFilterType() != null,getFirstResult(),PAGE_SIZE),getFirstResult(),
                employeeBusinessRepository.searchResultCount(getConditionAdapter().getCondition(),getConditionAdapter().getContains(),getConditionAdapter().isEmpty(),getDateFrom(),getDateFrom() != null,getDateTo(),getDateTo() != null,getFilterType(),getFilterType() != null),PAGE_SIZE);
    }
}
