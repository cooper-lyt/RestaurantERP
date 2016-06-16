package cc.coopersoft.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 6/16/16.
 */
@Entity
@Table(name = "DICTIONARY_CATEGAORY",catalog = "PLAT_SYSTEM")
public class DictionaryCategaory implements java.io.Serializable {

    private String id;
    private String managerRole;
    private String name;
    private boolean enable;
    private boolean system;
    private Date botime;
    private Set<Dictionary> dictionaries = new HashSet<Dictionary>(0);

    public DictionaryCategaory() {
    }


    public DictionaryCategaory(boolean enable, boolean system, Date botime) {
        this.enable = enable;
        this.system = system;
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

    @Column(name = "MANAGER_ROLE", length = 32)
    @Size(max = 32)
    public String getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(String managerRole) {
        this.managerRole = managerRole;
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

    @Column(name = "SYSTEM", nullable = false)
    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categaory")
    public Set<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Set<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }
}
