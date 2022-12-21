package com.example.books.issueStatus;

import com.example.books.issueStatus.converter.IssueStatusToIssueStatusViewConverter;
import com.example.books.issueStatus.web.IssueStatusBaseReq;
import com.example.books.issueStatus.web.IssueStatusView;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class IssueStatusService {
    private final IssueStatusRepository issueStatusRepository;
    private final IssueStatusToIssueStatusViewConverter issueStatusToIssueStatusViewConverter;
    private final MessageUtil messageUtil;

    public IssueStatusService(IssueStatusRepository issueStatusRepository,
                              IssueStatusToIssueStatusViewConverter issueStatusToIssueStatusViewConverter,
                              MessageUtil messageUtil) {
        this.issueStatusRepository = issueStatusRepository;
        this.issueStatusToIssueStatusViewConverter = issueStatusToIssueStatusViewConverter;
        this.messageUtil = messageUtil;
    }


    /*
    Формирование вывода статуса выдачи по id
     */
    public IssueStatusView getIssueStatus(Long id) {
        IssueStatus issueStatus = findIssueStatusOrThrow(id);
        return issueStatusToIssueStatusViewConverter.convert(issueStatus);
    }

    /*
    Поиск id
     */
    public IssueStatus findIssueStatusOrThrow(Long id) {
        return issueStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("issueStatus.NotFound", id)));
    }

    /*
    Формирование создания статуса выдачи
     */
    public IssueStatusView create(IssueStatusBaseReq req) {
        IssueStatus issueStatus = new IssueStatus();
        this.prepare(issueStatus, req);
        IssueStatus issueStatusSave = issueStatusRepository.save(issueStatus);
        return issueStatusToIssueStatusViewConverter.convert(issueStatusSave);
    }

    /*
    Формирование удаления статуса выдачи
     */
    @Transactional
    public void delete(Long id) {
        try {
            issueStatusRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("issueStatus.NotFound", id));
        }
    }

    /*
    Формирование обновления статуса выдачи
     */
    public IssueStatusView update(IssueStatus issueStatus, IssueStatusBaseReq req) {
        IssueStatus newIssueStatus = this.prepare(issueStatus,req);
        IssueStatus issueStatusSave = issueStatusRepository.save(newIssueStatus);
        return issueStatusToIssueStatusViewConverter.convert(issueStatusSave);
    }

    /*
    Преобразование данных
     */
    private IssueStatus prepare(IssueStatus issueStatus, IssueStatusBaseReq issueStatusBaseReq){
        issueStatus.setStatusName(issueStatusBaseReq.getStatusName());
        return issueStatus;
    }
}
