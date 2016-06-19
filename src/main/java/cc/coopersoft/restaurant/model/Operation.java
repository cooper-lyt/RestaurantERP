package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/19/16.
 */
@Entity
@Table(name = "OPERATION",catalog = "RESTAURANT")
public class Operation implements java.io.Serializable {

    public enum Type{
        APPLY
    }

    private String id;
    private String userId;
    private String userName;
    private String name;
    private Date operationTime;
    private Type type;

    private Business business;

    public Operation() {
    }

    public Operation(String id, String userId, String userName, String name, Date operationTime, Type type, Business business) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.operationTime = operationTime;
        this.type = type;
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

    @Column(name="USER_ID" , nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="USER_NAME" , nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name="NAME" , nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATION_TIME", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 12)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
