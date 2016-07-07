package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 7/8/16.
 */
@Entity
@Table(name = "EMPLOYEE_MONEY_GIFT",catalog = "RESTAURANT")
public class EmployeeGiftMoney implements java.io.Serializable {

    private String id;
    private BigDecimal money;
    private String category;

    private EmployeeGiftBalance employeeGiftBalance;



    public EmployeeGiftMoney() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BALANCE")
    public EmployeeGiftBalance getEmployeeGiftBalance() {
        return employeeGiftBalance;
    }

    public void setEmployeeGiftBalance(EmployeeGiftBalance employeeGiftBalance) {
        this.employeeGiftBalance = employeeGiftBalance;
    }

}
