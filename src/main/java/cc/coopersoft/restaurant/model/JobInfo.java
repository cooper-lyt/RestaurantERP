package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/20/16.
 */
@Entity
@Table(name = "JOB_INFO",catalog = "RESTAURANT")
public class JobInfo {

    private String id;
    private String jobs;
    private String level;
    private String workCode;

    private Office office;
    private EmployeeAction employeeAction;

    public JobInfo() {
    }

    public JobInfo(String jobs, String level, String workCode, Office office, EmployeeAction employeeAction) {
        this.jobs = jobs;
        this.level = level;
        this.workCode = workCode;
        this.office = office;
        this.employeeAction = employeeAction;
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

    @Column(name = "JOBS",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    @Column(name = "LEVEL",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "WORK_CODE",length = 32)
    @Size(max = 32)
    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "OFFICE", nullable = false)
    @NotNull
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "jobInfo",optional = false)
    @PrimaryKeyJoinColumn
    @NotNull
    public EmployeeAction getEmployeeAction() {
        return employeeAction;
    }

    public void setEmployeeAction(EmployeeAction employeeAction) {
        this.employeeAction = employeeAction;
    }

    @PrePersist
    public void ensureId(){
        this.id = getEmployeeAction().getId();
    }
}
