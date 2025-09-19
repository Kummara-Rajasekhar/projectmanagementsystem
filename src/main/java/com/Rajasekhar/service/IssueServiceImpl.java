package com.Rajasekhar.service;

import com.Rajasekhar.model.Issue;
import com.Rajasekhar.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository issueRepository;
    @Override
    public Optional<Issue> getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent()){
            return issue;
        }
        throw new Exception("No issues found with issueId"+issueId);
    }
}
