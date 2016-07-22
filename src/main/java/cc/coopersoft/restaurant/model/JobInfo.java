package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/20/16.
 */
@Entity
@Table(name = "JOB_INFO",catalog = "RESTAURANT")
public class JobInfo {

    private String id;
    private String workCode;

    private Office office;
    private Job job;
    private String level;

    public JobInfo() {
    }

    public JobInfo(String id) {
        this.id = id;
    }

    public JobInfo(String id, JobInfo jobInfo){
        this.id = id;
        this.workCode = jobInfo.getWorkCode();
        this.office = jobInfo.getOffice();
        this.job = jobInfo.getJob();
        this.level = jobInfo.getLevel();
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


    @Column(name = "WORK_CODE",length = 32)
    @Size(max = 32)
    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @Column(name = "LEVEL",length = 32,nullable = false)
    @Size(max = 32)
    @NotNull
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
    @JoinColumn(name = "OFFICE", nullable = false)
    @NotNull
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

}
