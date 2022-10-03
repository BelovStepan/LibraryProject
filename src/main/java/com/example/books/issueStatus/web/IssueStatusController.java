package com.example.books.issueStatus.web;

import com.example.books.issueStatus.IssueStatus;
import com.example.books.issueStatus.IssueStatusRepository;
import com.example.books.issueStatus.IssueStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/issue_status")
public class IssueStatusController {

    private final IssueStatusRepository issueStatusRepository;
    private final IssueStatusService issueStatusService;

    public IssueStatusController(IssueStatusRepository issueStatusRepository, IssueStatusService issueStatusService) {
        this.issueStatusRepository = issueStatusRepository;
        this.issueStatusService = issueStatusService;
    }

    //GET_ID
    @GetMapping("/{id}")
    @ResponseBody
    public IssueStatusView getIssueStatus(@PathVariable Long id) {
        return issueStatusService.getIssueStatus(id);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public IssueStatusView create(@RequestBody @Valid IssueStatusBaseReq req) {
        return issueStatusService.create(req);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssueStatus(@PathVariable Long id){
        issueStatusService.delete(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public IssueStatusView updateIssueStatus(@PathVariable(name = "id") Long id,
                                    @RequestBody @Valid IssueStatusBaseReq req){
        IssueStatus issueStatus = issueStatusService.findIssueStatusOrThrow(id);
        return issueStatusService.update(issueStatus, req);
    }
}
