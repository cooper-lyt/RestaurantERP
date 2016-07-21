package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 6/23/16.
 */
@Entity
@Table(name = "PAID_PROJECT",catalog = "RESTAURANT")
public class PaidProject implements java.io.Serializable, Comparable<PaidProject> {

    private String id;
    private String name;
    private Date botime;
    private BigDecimal fullWorkMoney;

    private OfficeType officeType;
    private Set<PaidItem> paidItems = new HashSet<PaidItem>(0);
    private Set<PaidContentItem> paidContentItems = new HashSet<PaidContentItem>(0);

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

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,mappedBy = "paidProject", cascade = CascadeType.ALL)
    public Set<PaidItem> getPaidItems() {
        return paidItems;
    }

    public void setPaidItems(Set<PaidItem> paidItems) {
        this.paidItems = paidItems;
    }

    @Column(name="FULL_WORK_MONEY",nullable = false)
    @NotNull
    public BigDecimal getFullWorkMoney() {
        return fullWorkMoney;
    }

    public void setFullWorkMoney(BigDecimal fullWorkMoney) {
        this.fullWorkMoney = fullWorkMoney;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "paidProject",orphanRemoval = true,cascade = CascadeType.ALL)
    public Set<PaidContentItem> getPaidContentItems() {
        return paidContentItems;
    }

    public void setPaidContentItems(Set<PaidContentItem> paidContentItems) {
        this.paidContentItems = paidContentItems;
    }

    public int compareTo(PaidProject o) {
        return getBotime().compareTo(o.getBotime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PaidProject)) return false;

        PaidProject that = (PaidProject) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Transient
    public boolean isHaveFullWorkMoney(){
        return getFullWorkMoney() != null && !BigDecimal.ZERO.equals(getFullWorkMoney());
    }
}
