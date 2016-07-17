package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 7/17/16.
 */

@Entity
@Table(name = "RES_CLASS",catalog = "RESTAURANT")
public class ResClass implements java.io.Serializable, Comparable<ResClass> {

    private String id;

    private String name;

    private boolean product;

    private boolean semi;

    private boolean raw;

    private String category;

    private Date botime;

    private Set<Res> reses = new HashSet<Res>(0);

    public ResClass() {
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


    @Column(name = "NAME", nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PRODUCT", nullable = false)
    public boolean isProduct() {
        return product;
    }

    public void setProduct(boolean produce) {
        this.product = produce;
    }

    @Column(name = "SEMI", nullable = false)
    public boolean isSemi() {
        return semi;
    }

    public void setSemi(boolean semi) {
        this.semi = semi;
    }

    @Column(name = "RAW", nullable = false)
    public boolean isRaw() {
        return raw;
    }

    public void setRaw(boolean raw) {
        this.raw = raw;
    }

    @Column(name = "CATEGORY", nullable = false)
    @NotNull
    @Size(max = 32)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "resClass",orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<Res> getReses() {
        return reses;
    }

    public void setReses(Set<Res> reses) {
        this.reses = reses;
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


    public int compareTo(ResClass o) {
        return getBotime().compareTo(o.getBotime());
    }
}
