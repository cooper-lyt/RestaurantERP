package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 6/19/16.
 */
@Entity
@Table(name = "BUSINESS",catalog = "RESTAURANT")
public class Business {

    public enum Type{
        EMP_JOIN,EMP_JOB_CHANGE,EMP_LEAVE
    }

    public enum Status{
        COMPLETE
    }

    private String id;
    private Type type;
    private Status status;
    private Date startTime;

    private Set<EmployeeAction> employeeActions = new HashSet<EmployeeAction>(0);
    private Set<Operation> operations = new HashSet<Operation>(0);

    public Business() {
    }

    public Business(String id, Type type, Status status, Date startTime) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.startTime = startTime;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 16)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "business", orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<EmployeeAction> getEmployeeActions() {
        return employeeActions;
    }

    public void setEmployeeActions(Set<EmployeeAction> employeeActions) {
        this.employeeActions = employeeActions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "business", orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
