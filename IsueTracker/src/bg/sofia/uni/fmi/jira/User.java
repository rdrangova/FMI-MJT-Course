package bg.sofia.uni.fmi.jira;

import java.util.Objects;

public class User {
    private String name;

    public User(String userName) {
        this.name = userName;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}

