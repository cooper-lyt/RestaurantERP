package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/10/16.
 */
@Entity
@Table(name = "EMPLOYEE",catalog = "RESTAURANT")
public class Employee implements java.io.Serializable , Comparable<Employee>{

    public enum Status{
        NORMAL,LEAVE
    }

    private String id;
    private String name;
    private String phone;
    private Status status;
    private Date joinDate;
    private String workCode;

    private Job job;
    private String level;

    private Office office;

    public Employee() {
    }

    public Employee(Status status, Date joinDate) {
        this.status = status;
        this.joinDate = joinDate;
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

    @Column(name = "NAME",nullable = false,length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PHONE",length = 32)
    @Size(max = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",nullable = false,length = 12)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOIN_DATE", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
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

    @Column(name = "WORK_CODE",length = 32)
    @Size(max = 32)
    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @Column(name = "LEVEL",length = 32,nullable = false)
    @Size(max = 32)
    @NotNull
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "JOB", nullable = false)
    @NotNull
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int compareTo(Employee o) {
        return getJoinDate().compareTo(o.getJoinDate());
    }


}
