package cc.coopersoft.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/23/16.
 */

@Entity
@Table(name = "OFFICE_TYPE",catalog = "RESTAURANT")
public class OfficeType {

    private String id;
    private String name;
    private boolean store;
    private boolean produce;
    private boolean sale;
    private Date botime;
    private boolean disable;


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

    @Column(name = "DISABLE",nullable = false)
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
