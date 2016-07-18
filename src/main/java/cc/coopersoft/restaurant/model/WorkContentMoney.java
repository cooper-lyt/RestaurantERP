package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 7/18/16.
 */
@Entity
@Table(name = "RES_CLASS",catalog = "RESTAURANT")
public class WorkContentMoney implements java.io.Serializable {

    private String id;

    private BigDecimal money;

    private PaidBalance paidBalance;

    private Res res;

    private BigDecimal count;

    private BigDecimal price;

    public WorkContentMoney() {
    }

    public WorkContentMoney(String id) {
        this.id = id;
        this.count = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
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

    @Column(name="MONEY",nullable = false)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BALANCE", nullable = false)
    public PaidBalance getPaidBalance() {
        return paidBalance;
    }

    public void setPaidBalance(PaidBalance paidBalance) {
        this.paidBalance = paidBalance;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="RES",nullable = false)
    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    @Column(name="COUNT",nullable = false)
    @NotNull
    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Column(name = "PRICE", nullable = false)
    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
