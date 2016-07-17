package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 7/17/16.
 */

@Entity
@Table(name = "PAID_CONTENT_ITEM",catalog = "RESTAURANT")
public class PaidContentItem implements java.io.Serializable {

    private String id;
    private Res res;
    private PaidProject paidProject;
    private BigDecimal money;

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

    @Column(name = "MONEY",nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "RES", nullable = false)
    @NotNull
    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="PAID_PROJECT", nullable = false)
    @NotNull
    public PaidProject getPaidProject() {
        return paidProject;
    }

    public void setPaidProject(PaidProject paidProject) {
        this.paidProject = paidProject;
    }
}
