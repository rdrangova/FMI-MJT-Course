package bg.sofia.uni.fmi.mjt.git;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Branch {
    private String name;
    LinkedHashMap<String, Commit> commits;
    private Commit lastCommit;


    public Branch(String name) {
        this.name = name;
        commits = new LinkedHashMap<>();
        this.lastCommit = null;
    }

    public void copyCommits(Branch other) {
        this.commits = new LinkedHashMap<>(other.commits);
    }

    public String getBranch() {
        return name;
    }

    public void put(String commitName, HashSet<String> files) {
        Commit toBeCommitted = new Commit(commitName);
        toBeCommitted.filesInCommit.addAll(files);
        commits.put(toBeCommitted.getHash(), toBeCommitted);
    }

    public Commit getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(String commitName) {
        this.lastCommit = new Commit(commitName);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(name, branch.name) &&
                Objects.equals(commits, branch.commits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, commits);
    }
}
