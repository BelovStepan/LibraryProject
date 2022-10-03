package com.example.books.issueStatus.web;

import com.example.books.base.BaseRequest;
import javax.validation.constraints.NotEmpty;

public class IssueStatusBaseReq extends BaseRequest {

    @NotEmpty
    private String statusName;

    public String getStatusName() {return statusName;}

    public void setStatusName(String statusName) {this.statusName = statusName;}
}
