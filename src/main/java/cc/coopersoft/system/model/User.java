package cc.coopersoft.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/4/16.
 */
@Entity
@Table(name = "USER", catalog = "PLAT_SYSTEM")
public class User implements java.io.Serializable{


    private String name;
    private String id;
    private String email;
    private String phone;
    private String password;

    public User() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "EMAIL",length = 50)
    @Size(max = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Phone",length = 50)
    @Size(max = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Column(name = "PASSWORD",length = 50)
    @Size(max = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
