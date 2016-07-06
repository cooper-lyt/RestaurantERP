package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.ChoiceList;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.operation.OfficeHome;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by cooper on 7/6/16.
 */
@Named
@ConversationScoped
public class EmployeeChoice extends ChoiceList<Employee> {


    @Inject
    private OfficeHome officeHome;

    @Inject
    private EmployeeRepository employeeRepository;

    private String jobId;

    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    protected List<Employee> getOrgData() {
        return employeeRepository.findByOffice(officeHome.getInstance().getId(),jobId,(jobId != null && !"".equals(jobId.trim())),level, (level != null && !"".equals(level.trim())));
    }


}
