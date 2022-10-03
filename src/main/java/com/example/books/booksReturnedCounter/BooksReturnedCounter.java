package com.example.books.booksReturnedCounter;

import com.example.books.issueStatus.IssueStatus;
import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.returnOfBooks.ReturnOfBooks;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "books_returned_counter")
public class BooksReturnedCounter {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "books_returned_counter_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "books_returned_counter_id_seq"),
                    @org.hibernate.annotations.Parameter(name= "INCREMENT", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_returned_counter_id_seq")
    private Long id;

    @Column(name = "quantity_returned")
    private int quantityReturned;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_issue")
    private IssueStatus issueStatus;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_extradition")
    private LoanOfBooks loanOfBooks;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_return")
    private ReturnOfBooks returnOfBooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public LoanOfBooks getLoanOfBooks() {
        return loanOfBooks;
    }

    public void setLoanOfBooks(LoanOfBooks loanOfBooks) {
        this.loanOfBooks = loanOfBooks;
    }

    public ReturnOfBooks getReturnOfBooks() {
        return returnOfBooks;
    }

    public void setReturnOfBooks(ReturnOfBooks returnOfBooks) {
        this.returnOfBooks = returnOfBooks;
    }
}
