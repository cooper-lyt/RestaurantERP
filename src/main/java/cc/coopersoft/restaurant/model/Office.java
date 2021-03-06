package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by cooper on 6/10/16.
 */
@Entity
@Table(name = "OFFICE",catalog = "RESTAURANT")
public class Office implements java.io.Serializable {
    //STATUS varchar(12) NOT NULL,

    public enum OfficeStatus{
        PREPARE,OPEN,CLOSE,DESTROY
    }

    private String id;
    private String name;
    private Date botime;
    private OfficeStatus status;

    private OfficeType type;

    public Office() {
    }

    public Office(Date botime) {
        this.botime = botime;
    }

    public Office(Date botime, OfficeStatus status) {
        this.botime = botime;
        this.status = status;
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

    @Column(name = "NAME",nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 12)
    @NotNull
    public OfficeStatus getStatus() {
        return status;
    }

    public void setStatus(OfficeStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "OFFICE_TYPE",nullable = false)
    @NotNull
    public OfficeType getType() {
        return type;
    }

    public void setType(OfficeType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Office)) return false;
        Office office = (Office) o;
        return getId() != null ? getId().equals(office.getId()) : office.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
