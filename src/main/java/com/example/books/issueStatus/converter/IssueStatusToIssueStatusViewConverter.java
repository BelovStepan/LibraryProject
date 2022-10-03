package com.example.books.issueStatus.converter;

import com.example.books.issueStatus.IssueStatus;
import com.example.books.issueStatus.web.IssueStatusView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class IssueStatusToIssueStatusViewConverter implements Converter<IssueStatus, IssueStatusView> {

    @Override
    public IssueStatusView convert(@NonNull IssueStatus issueStatus) {
        IssueStatusView view = new IssueStatusView();
        view.setId(issueStatus.getId());
        view.setStatusName(issueStatus.getStatusName());
        return view;
    }
}