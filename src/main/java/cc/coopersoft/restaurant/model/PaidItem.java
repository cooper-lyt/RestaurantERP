package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 6/23/16.
 */
@Entity
@Table(name = "PAID_ITEM",catalog = "RESTAURANT")
public class PaidItem implements java.io.Serializable, Comparable<PaidItem>{

    //TODO:  type enum day_calc ones join_date

    private String id;
    private String category;
    private String level;

    private Job job;
    private PaidProject paidProject;

    private BigDecimal money;

    public PaidItem() {
    }

    public PaidItem(String id, String category, String level, Job job, PaidProject paidProject, BigDecimal money) {
        this.id = id;
        this.category = category;
        this.level = level;
        this.job = job;
        this.paidProject = paidProject;
        this.money = money;
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


    @Column(name = "CATEGORY",nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "LEVEL",length = 32)
    @Size(max = 32)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "JOB", nullable = false)
    @NotNull
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PAID_PROJECT",nullable = false)
    @NotNull
    public PaidProject getPaidProject() {
        return paidProject;
    }

    public void setPaidProject(PaidProject paidProject) {
        this.paidProject = paidProject;
    }


    @Column(name = "MONEY",nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int compareTo(PaidItem o) {
        if (getPaidProject().equals(o.getPaidProject())){
            if (getCategory().equals(o.getCategory())){
                if(getJob().equals(o.getJob())){
                    return getLevel().compareTo(o.getLevel());
                }else{
                    return getJob().compareTo(o.getJob());
                }
            }else {
                return getCategory().compareTo(o.getCategory());
            }

        }else{
            return getPaidProject().compareTo(o.getPaidProject());
        }
    }
}
