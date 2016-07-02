package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/23/16.
 */

@Entity
@Table(name = "JOB",catalog = "RESTAURANT")
public class Job implements java.io.Serializable, Comparable<Job> {

    private String id;
    private String name;
    private int pri;
    private boolean enable;

    private OfficeType officeType;

    public Job() {
    }

    public Job(String id,String name, int pri, boolean enable, OfficeType officeType) {
        this.id = id;
        this.pri = pri;
        this.name = name;
        this.enable = enable;
        this.officeType = officeType;
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

    @Column(name = "PRI",nullable = false)
    public int getPri() {
        return pri;
    }

    public void setPri(int pri) {
        this.pri = pri;
    }

    @Column(name = "ENABLE",nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean disable) {
        this.enable = disable;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "OFFICE_TYPE",nullable = false)
    @NotNull
    public OfficeType getOfficeType() {
        return officeType;
    }

    public void setOfficeType(OfficeType officeType) {
        this.officeType = officeType;
    }

    public int compareTo(Job o) {
        return Integer.valueOf(getPri()).compareTo(o.getPri()) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Job)) return false;

        Job job = (Job) o;

        return getId() != null ? getId().equals(job.getId()) : job.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
