package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/19/16.
 */
@Entity
@Table(name = "EMPLOYEE_ACTION",catalog = "RESTAURANT")
public class EmployeeAction implements java.io.Serializable{

    private String id;
    private Date validTime;

    private Employee employee;
    private Business business;

    public EmployeeAction() {
    }

    public EmployeeAction(String id, Date validTime, Employee employee, Business business) {
        this.id = id;
        this.validTime = validTime;
        this.employee = employee;
        this.business = business;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_TIME", length = 19, columnDefinition = "DATETIME")
    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID",nullable = false)
    @NotNull
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
