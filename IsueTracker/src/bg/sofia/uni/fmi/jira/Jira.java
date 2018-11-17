package bg.sofia.uni.fmi.jira;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Issue;

import java.time.LocalDateTime;

public class Jira implements IssueTracker {
    private Issue[] issues;

    public Jira(Issue[] issues2) {
        issues = new Issue[issues2.length];
        //System.out.println(issues2.length);
        for (int i = 0; i < issues.length; i++) {
            issues[i] = issues2[i];
        }
    }

    @Override
    public Issue[] findAll(Component component, IssueStatus status) {
        Issue[] equalStatusIssues = new Issue[issues.length];
        int count = 0;
        for (Issue issue : issues) {
            if (issue != null && issue.getComponent().equals(component) && issue.getStatus().equals(status)) {
                equalStatusIssues[count++] = issue;
            }
        }
        return equalStatusIssues;
    }

    @Override
    public Issue[] findAll(Component component, IssuePriority priority) {
        Issue[] equalPriorityIssues = new Issue[issues.length];
        int count = 0;
        for (Issue issue : issues) {
            if (issue != null && issue.getComponent().equals(component) && issue.getPriority() == priority) {
                equalPriorityIssues[count] = issue;
                count++;
            }
        }
        return equalPriorityIssues;
    }

    @Override
    public Issue[] findAll(Component component, IssueType type) {

        Issue[] equalTypeIssues = new Issue[issues.length];
        int count = 0;
        for (Issue i : issues) {
            if (i != null && i.getComponent().equals(component) && i.getType().equals(type)) {
                equalTypeIssues[count] = i;
                count++;
            }
        }
        return equalTypeIssues;
    }

    @Override
    public Issue[] findAll(Component component, IssueResolution resolution) {

        Issue[] equalResolutionIssues = new Issue[issues.length];
        int count = 0;
        for (Issue i : issues) {
            if (i != null && i.getComponent().equals(component) && i.getResolution().equals(resolution)) {
                equalResolutionIssues[count] = i;
                count++;
            }
        }
        return equalResolutionIssues;
    }

    @Override
    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        Issue[] equalPeriodIssues = new Issue[issues.length];
        int count = 0;
        for (Issue i : issues) {
            if (startTime != null && endTime != null && i != null
                    && i.getCreatedAt().isAfter(startTime) && i.getLastModifiedAt().isBefore(endTime)) {
                equalPeriodIssues[count] = i;
                count++;
            }
        }
        return equalPeriodIssues;
    }

    @Override
    public Issue[] findAllBefore(LocalDateTime dueTime) {
        Issue[] equalDueIssues = new Issue[issues.length];
        int count = 0;
        for (Issue i : issues) {
            if (null != i && dueTime != null && i.getDueTime().isBefore(dueTime)) {
                equalDueIssues[count] = i;
                count++;
            }
        }
        return equalDueIssues;
    }
}
