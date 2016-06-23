package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/23/16.
 */
@Entity
@Table(name = "JOB_LEVEL",catalog = "RESTAURANT")
public class JobLevel {

    private String id;
    private String name;
    private int level;
    private boolean disable;

    private Job job;

    public JobLevel() {
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

    @Column(name="NAME",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "LEVEL", nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name="DISABLE",nullable = false)
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "JOBS", nullable = false)
    @NotNull
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
