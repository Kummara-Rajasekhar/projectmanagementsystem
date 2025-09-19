package com.Rajasekhar.service;

import com.Rajasekhar.model.Issue;

import java.util.Optional;

public interface IssueService {
    Optional<Issue> getIssueById(Long issueId)throws Exception;
}
