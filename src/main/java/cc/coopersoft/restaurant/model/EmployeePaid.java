package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 7/20/16.
 */
@Entity
@Table(name = "EMPLOYEE_PAID",catalog = "RESTAURANT")
public class EmployeePaid implements java.io.Serializable {

    private String id;
    private BigDecimal totalMoney;
    private BigDecimal paidMoney;

    private EmployeeAction employeeAction;
    private Set<PaidBalance> paidBalances = new HashSet<PaidBalance>(0);

    public EmployeePaid() {
    }

    public EmployeePaid(EmployeeAction employeeAction) {
        this.employeeAction = employeeAction;
        paidMoney = BigDecimal.ZERO;
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

    @Column(name = "TOTAL_MONEY" , nullable = false)
    @NotNull
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Column(name="PAID_MONEY", nullable = false)
    @NotNull
    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "employeePaid",optional = false)
    @PrimaryKeyJoinColumn
    @NotNull
    public EmployeeAction getEmployeeAction() {
        return employeeAction;
    }

    public void setEmployeeAction(EmployeeAction employeeAction) {
        this.employeeAction = employeeAction;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "employeePaid" ,cascade = CascadeType.ALL)
    public Set<PaidBalance> getPaidBalances() {
        return paidBalances;
    }

    public void setPaidBalances(Set<PaidBalance> paidBalances) {
        this.paidBalances = paidBalances;
    }


    @PrePersist
    public void ensureId(){
        this.id = getEmployeeAction().getId();
    }
}
