package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 7/18/16.
 */
@Entity
@Table(name = "WORK_CONTENT_MONEY",catalog = "RESTAURANT")
public class WorkContentMoney implements java.io.Serializable {

    private String id;

    private BigDecimal money;

    private PaidBalance paidBalance;

    private Res res;

    private BigDecimal count;

    private BigDecimal price;

    private String workCode;

    public WorkContentMoney() {
    }

    public WorkContentMoney(String id, String workCode) {
        this.id = id;
        this.count = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
        this.workCode = workCode;
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

    @Column(name = "WORK_CODE", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }
}
