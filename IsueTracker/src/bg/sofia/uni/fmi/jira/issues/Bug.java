package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class Bug extends Issue {
    public Bug(IssuePriority priority, Component component, User name, String description) throws InvalidReporterException {
        super(priority, component, name, description);
    }

    @Override
    public IssueType getType() {
        return IssueType.BUG;
    }

    @Override
    public LocalDateTime getDueTime() {
        System.out.println("Bugs don't have due time");
        return null;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return creationTime;
    }

    @Override
    public LocalDateTime getLastModifiedAt() {
        return lastChangeTime;
    }
}
