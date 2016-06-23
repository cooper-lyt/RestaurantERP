package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/23/16.
 */
@Entity
@Table(name = "PAID_PROJECT",catalog = "RESTAURANT")
public class PaidProject implements java.io.Serializable {

    private String id;
    private String name;
    private Date botime;

    private OfficeType officeType;

    public PaidProject() {
    }

    public PaidProject(String id, Date botime) {
        this.id = id;
        this.botime = botime;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BOTIME", nullable = false, length = 19, columnDefinition = "DATETIME")
    @NotNull
    public Date getBotime() {
        return botime;
    }

    public void setBotime(Date botime) {
        this.botime = botime;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OFFICE_TYPE", nullable = false)
    @NotNull
    public OfficeType getOfficeType() {
        return officeType;
    }

    public void setOfficeType(OfficeType officeType) {
        this.officeType = officeType;
    }
}
