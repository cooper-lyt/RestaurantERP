package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by cooper on 6/23/16.
 */

@Entity
@Table(name = "OFFICE_TYPE",catalog = "RESTAURANT")
public class OfficeType implements java.io.Serializable{

    private String id;
    private String name;
    private boolean store;
    private boolean produce;
    private boolean sale;
    private Date botime;
    private boolean enable;

    private Set<Job> jobs = new HashSet<Job>(0);

    public OfficeType(String id, Date botime, boolean enable) {
        this.id = id;
        this.botime = botime;
        this.enable = enable;
    }

    public OfficeType() {
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

    @Column(name = "STORE",nullable = false)
    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    @Column(name = "PRODUCE",nullable = false)
    public boolean isProduce() {
        return produce;
    }

    public void setProduce(boolean produce) {
        this.produce = produce;
    }

    @Column(name = "SALE",nullable = false)
    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BOTIME", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getBotime() {
        return botime;
    }

    public void setBotime(Date botime) {
        this.botime = botime;
    }

    @Column(name = "ENABLE",nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "officeType" ,orphanRemoval = true,cascade = CascadeType.ALL)
    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    @Transient
    public List<Job> getEnableJobList(){
        List<Job> result = new ArrayList<Job>();
        for(Job job: getJobs()){
            if (job.isEnable()){
                result.add(job);
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof OfficeType)) return false;

        OfficeType that = (OfficeType) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
