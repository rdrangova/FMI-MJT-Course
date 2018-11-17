package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class NewFeature extends Issue {
    private LocalDateTime dueTime;

    public NewFeature(IssuePriority priority, Component component, User reporter, String description, LocalDateTime dueT) throws InvalidReporterException {
        super(priority, component, reporter, description);
        this.dueTime = dueT;
    }


    @Override
    public IssueType getType() {
        return IssueType.NEW_FEATURE;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return creationTime;
    }

    @Override
    public LocalDateTime getLastModifiedAt() {
        return lastChangeTime;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }
}
