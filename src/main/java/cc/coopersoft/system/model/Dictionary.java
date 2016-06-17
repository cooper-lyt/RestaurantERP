package cc.coopersoft.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/16/16.
 */
@Entity
@Table(name = "DICTIONARY",catalog = "PLAT_SYSTEM")
public class Dictionary implements java.io.Serializable, Comparable<Dictionary> {

    private String id;
    private String name;
    private boolean enable;
    private int pri;

    private DictionaryCategaory categaory;

    public Dictionary() {
    }

    public Dictionary(boolean enable) {
        this.enable = enable;
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

    @Column(name = "NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ENABLE", nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Column(name = "PRI", nullable = false)
    public int getPri() {
        return pri;
    }

    public void setPri(int pri) {
        this.pri = pri;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CATEGORY", nullable = false)
    @NotNull
    public DictionaryCategaory getCategaory() {
        return categaory;
    }

    public void setCategaory(DictionaryCategaory categaory) {
        this.categaory = categaory;
    }

    public int compareTo(Dictionary o) {
        return Integer.valueOf(getPri()).compareTo(o.getPri());
    }
}
