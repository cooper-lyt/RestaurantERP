package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by cooper on 6/18/16.
 */
@Named
@ConversationScoped
public class EmployeeHome extends EntityHome<Employee,String> {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private FacesContext facesContext;

    protected Employee createInstance() {
        return new Employee(Employee.Status.NORMAL,new Date());
    }

    protected EntityRepository<Employee, String> getEntityRepository() {
        return employeeRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("employeeId"));
    }

}
