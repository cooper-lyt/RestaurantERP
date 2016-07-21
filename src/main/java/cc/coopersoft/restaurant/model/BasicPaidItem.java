package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 7/8/16.
 */

@Entity
@Table(name = "BASIC_PAID_ITEM",catalog = "RESTAURANT")
public class BasicPaidItem implements java.io.Serializable {

    private String id;
    private String category;
    private BigDecimal money;
    private BigDecimal calcParam;

    private PaidBalance paidBalance;

    public BasicPaidItem() {
    }

    public BasicPaidItem(String id, String category, BigDecimal calcParam, BigDecimal money, PaidBalance paidBalance) {
        this.id = id;
        this.category = category;
        this.money = money;
        this.paidBalance = paidBalance;
        this.calcParam = calcParam;
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

    @Column(name = "CATEGORY",nullable = false)
    @NotNull
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    @Column(name = "CALC_PARAM", nullable = false)
    @NotNull
    public BigDecimal getCalcParam() {
        return calcParam;
    }

    public void setCalcParam(BigDecimal calcParam) {
        this.calcParam = calcParam;
    }
}
