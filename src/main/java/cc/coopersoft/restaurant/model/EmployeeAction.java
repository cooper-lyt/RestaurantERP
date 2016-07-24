package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cooper on 6/19/16.
 */
@Entity
@Table(name = "EMPLOYEE_ACTION",catalog = "RESTAURANT")
public class EmployeeAction implements Comparable<EmployeeAction>,java.io.Serializable{

    private String id;
    private Date validTime;

    private Employee employee;
    private Business business;
    private JobInfo jobInfo;
    private JobInfo newJob;
    private PaidBalance paidBalance;
    private EmployeeGiftMoney employeeGiftMoney;
    private EmployeePaid employeePaid;

    public EmployeeAction() {
    }

    public EmployeeAction(String id, Date validTime, Employee employee, Business business) {
        this.id = id;
        this.validTime = validTime;
        this.employee = employee;
        this.business = business;
        this.jobInfo = employee.getJobInfo();
    }

    public EmployeeAction(String id, Date validTime, Employee employee) {
        this.id = id;
        this.validTime = validTime;
        this.employee = employee;
        this.jobInfo = employee.getJobInfo();
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

    @ManyToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
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

    @ManyToOne(fetch = FetchType.LAZY,optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "JOB_INFO", nullable = false)
    @NotNull
    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "NEW_JOB")
    public JobInfo getNewJob() {
        return newJob;
    }

    public void setNewJob(JobInfo newJob) {
        this.newJob = newJob;
    }

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public PaidBalance getPaidBalance() {
        return paidBalance;
    }

    public void setPaidBalance(PaidBalance paidBalance) {
        this.paidBalance = paidBalance;
    }

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public EmployeeGiftMoney getEmployeeGiftMoney() {
        return employeeGiftMoney;
    }

    public void setEmployeeGiftMoney(EmployeeGiftMoney employeeGiftMoney) {
        this.employeeGiftMoney = employeeGiftMoney;
    }

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public EmployeePaid getEmployeePaid() {
        return employeePaid;
    }

    public void setEmployeePaid(EmployeePaid employeePaid) {
        this.employeePaid = employeePaid;
    }

    public int compareTo(EmployeeAction o) {
        return getEmployee().compareTo(o.getEmployee());
    }
}
