package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IIssue;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Issue implements IIssue {

    private String id;
    private static int counter = -1;
    private IssuePriority priority;
    private IssueStatus status;
    private IssueType type;
    private IssueResolution resolution;
    private Component component;
    LocalDateTime creationTime;
    LocalDateTime lastChangeTime;
    private User reporter;
    private String description;


    public Issue(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException {

        if (reporter.getName() == null) {
            throw new InvalidReporterException();
        } else
            this.reporter = reporter;//throw exception somehow

        if (priority == null) {
            throw new IllegalArgumentException("The priority cannot be null");
        } else {
            this.priority = priority;
        }

        if (component == null) {
            throw new IllegalArgumentException("The component cannot be null");
        } else {
            this.component = component;
        }

        if (description == null) {
            throw new IllegalArgumentException("The description cannot be null");
        } else {
            this.description = description;
        }


        this.resolution = IssueResolution.UNRESOLVED;
        this.status = IssueStatus.OPEN;
        this.id = UniqueIdGenerator();
    }


    public String UniqueIdGenerator() {
        return component.getShortName() + "-" + (counter++);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    @Override
    public String getId() {
        return id;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public Component getComponent() {
        return component;
    }

    public abstract IssueType getType();

    public abstract LocalDateTime getDueTime();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id) &&
                priority == issue.priority &&
                status == issue.status &&
                type == issue.type &&
                resolution == issue.resolution &&
                Objects.equals(component, issue.component) &&
                Objects.equals(creationTime, issue.creationTime) &&
                Objects.equals(lastChangeTime, issue.lastChangeTime) &&
                Objects.equals(reporter, issue.reporter) &&
                Objects.equals(description, issue.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, status, type, resolution, component, creationTime, lastChangeTime, reporter, description);
    }
}
