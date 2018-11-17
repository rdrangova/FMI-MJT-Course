package bg.sofia.uni.fmi.jira.interfaces;

import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;

import java.time.LocalDateTime;

public interface IIssue {

    public void resolve(IssueResolution resolution);

    public void setStatus(IssueStatus status);

    public String getId();

    public LocalDateTime getCreatedAt();

    public LocalDateTime getLastModifiedAt();
}