package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/23/16.
 */
@Entity
@Table(name = "PAID_ITEM",catalog = "RESTAURANT")
public class PaidItem implements java.io.Serializable{

    //TODO:  type enum day_calc ones join_date

    private String id;
    private String category;

    private JobLevel jobLevel;
    private PaidProject paidProject;

    public PaidItem() {
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

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "JOB_LEVEL",nullable = false)
    @NotNull
    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
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
}
