package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 7/17/16.
 */
@Entity
@Table(name = "RES",catalog = "RESTAURANT")
public class Res implements java.io.Serializable, Comparable<Res> {

    private String id;

    private String name;

    private ResClass resClass;

    private boolean enable;

    private Date botime;

    public Res() {
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "RES_CLASS", nullable = false)
    @NotNull
    public ResClass getResClass() {
        return resClass;
    }

    public void setResClass(ResClass resClass) {
        this.resClass = resClass;
    }

    @Column(name = "ENABLE", nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int compareTo(Res o) {
        int result = getResClass().compareTo(o.getResClass());
        if (result == 0){
            return getBotime().compareTo(o.getBotime());
        }else{
            return result;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Res)) return false;

        Res res = (Res) o;

        return getId() != null ? getId().equals(res.getId()) : res.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
