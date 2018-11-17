package bg.sofia.uni.fmi.jira;


import java.util.Objects;

public class Component {

    private String name;
    private String shortName;
    private User creator;

    public Component(String name, String shortName, User creator) {
        this.name = name;
        this.shortName = shortName;
        //assert null != null;
        //assert this.creator != null;
        this.creator = creator;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name) &&
                Objects.equals(shortName, component.shortName) &&
                Objects.equals(creator, component.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortName, creator);
    }
}
