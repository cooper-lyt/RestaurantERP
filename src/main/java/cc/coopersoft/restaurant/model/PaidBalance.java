package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 7/8/16.
 */

@Entity
@Table(name = "PAID_BALANCE",catalog = "RESTAURANT")
public class PaidBalance implements java.io.Serializable {

    private String id;
    private BigDecimal workContentMoney;
    private BigDecimal workDay;
    private BigDecimal workFullMoney;
    private BigDecimal totalMoney;

    private EmployeeAction employeeAction;

    private Set<BasicPaidItem> basicPaidItems = new HashSet<BasicPaidItem>(0);
    private Set<EmployeeGiftBalance> employeeGiftBalances = new HashSet<EmployeeGiftBalance>(0);

    public PaidBalance() {
    }

    public PaidBalance(EmployeeAction employeeAction) {
        this.workContentMoney = BigDecimal.ZERO;
        this.workDay = BigDecimal.ZERO;
        this.workFullMoney = BigDecimal.ZERO;
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

    @Column(name = "WORK_CONTENT_MONEY",nullable = false)
    @NotNull
    public BigDecimal getWorkContentMoney() {
        return workContentMoney;
    }

    public void setWorkContentMoney(BigDecimal workContentMoney) {
        this.workContentMoney = workContentMoney;
    }

    @Column(name = "WORK_DAY",nullable = false)
    @NotNull
    public BigDecimal getWorkDay() {
        return workDay;
    }

    public void setWorkDay(BigDecimal workDay) {
        this.workDay = workDay;
    }


    @Column(name = "FULL_WORK_MONEY",nullable = false)
    @NotNull
    public BigDecimal getWorkFullMoney() {
        return workFullMoney;
    }

    public void setWorkFullMoney(BigDecimal workFullMoney) {
        this.workFullMoney = workFullMoney;
    }

    @Column(name = "TOTAL_MONEY",nullable = false)
    @NotNull
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "paidBalance",optional = false)
    @PrimaryKeyJoinColumn
    @NotNull
    public EmployeeAction getEmployeeAction() {
        return employeeAction;
    }

    public void setEmployeeAction(EmployeeAction employeeAction) {
        this.employeeAction = employeeAction;
    }

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "paidBalance",cascade = CascadeType.ALL)
    public Set<BasicPaidItem> getBasicPaidItems() {
        return basicPaidItems;
    }

    public void setBasicPaidItems(Set<BasicPaidItem> basicPaidItems) {
        this.basicPaidItems = basicPaidItems;
    }

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "paidBalance",cascade = CascadeType.ALL)
    public Set<EmployeeGiftBalance> getEmployeeGiftBalances() {
        return employeeGiftBalances;
    }

    public void setEmployeeGiftBalances(Set<EmployeeGiftBalance> employeeGiftBalances) {
        this.employeeGiftBalances = employeeGiftBalances;
    }


    @PrePersist
    public void ensureId(){
        this.id = getEmployeeAction().getId();
    }
}
