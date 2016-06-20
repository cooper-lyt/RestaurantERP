package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.operation.OfficeHome;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 6/20/16.
 */
@Named
public class EmployeeList {

    @Inject
    private OfficeHome officeHome;

    private List<Employee> resultList = new ArrayList<Employee>();




}
