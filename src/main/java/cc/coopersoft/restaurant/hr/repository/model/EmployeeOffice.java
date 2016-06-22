package cc.coopersoft.restaurant.hr.repository.model;

/**
 * Created by cooper on 6/22/16.
 */
public class EmployeeOffice implements java.io.Serializable{

    private String id;

    private String name;

    private Long count;

    public EmployeeOffice(String id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeOffice that = (EmployeeOffice) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
