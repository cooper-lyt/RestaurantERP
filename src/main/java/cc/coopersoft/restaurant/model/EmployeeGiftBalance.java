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
@Table(name = "EMPLOYEE_GIFT_BALANCE",catalog = "RESTAURANT")
public class EmployeeGiftBalance implements java.io.Serializable {

    private String id;
    private BigDecimal money;
    private String category;

    private PaidBalance paidBalance;
    private Set<EmployeeGiftMoney> employeeGiftMoneys = new HashSet<EmployeeGiftMoney>(0);

    public EmployeeGiftBalance() {
    }

    public EmployeeGiftBalance(String id, String category, PaidBalance paidBalance) {
        this.id = id;
        this.category = category;
        this.paidBalance = paidBalance;
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

    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Column(name = "CATEGORY",nullable = false)
    @NotNull
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BALANCE",nullable = false)
    @NotNull
    public PaidBalance getPaidBalance() {
        return paidBalance;
    }

    public void setPaidBalance(PaidBalance paidBalance) {
        this.paidBalance = paidBalance;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "employeeGiftBalance", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    public Set<EmployeeGiftMoney> getEmployeeGiftMoneys() {
        return employeeGiftMoneys;
    }

    public void setEmployeeGiftMoneys(Set<EmployeeGiftMoney> employeeGiftMoneys) {
        this.employeeGiftMoneys = employeeGiftMoneys;
    }
}
