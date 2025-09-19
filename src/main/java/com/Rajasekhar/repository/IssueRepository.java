package com.Rajasekhar.repository;

import com.Rajasekhar.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
