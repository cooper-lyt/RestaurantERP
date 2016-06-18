package cc.coopersoft.restaurant.hr.model;

import cc.coopersoft.restaurant.operation.model.Office;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/10/16.
 */
@Entity
@Table(name = "EMPLOYEE",catalog = "RESTAURANT")
public class Employee {

    public enum Status{
        NORMAL,LEAVE
    }

    private String id;
    private String name;
    private String jobs;
    private String level;
    private String phone;
    private Status status;
    private Date moneyBeginDay;
    private Date joinDate;

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
    @Column(name = "MONEY_BEGIN_DAY", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getMoneyBeginDay() {
        return moneyBeginDay;
    }

    public void setMoneyBeginDay(Date moneyBeginDay) {
        this.moneyBeginDay = moneyBeginDay;
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
}
