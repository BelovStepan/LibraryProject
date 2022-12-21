package com.example.books.loanOfBooks;

import com.example.books.book.Books;
import com.example.books.readers.Readers;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "loan_of_books")
public class LoanOfBooks {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "loan_of_books_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "loan_of_books_id_seq"),
                    @Parameter(name= "INCREMENT", value = "1"),
                    @Parameter(name = "MINVALUE", value = "1"),
                    @Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_of_books_id_seq")
    private Long id;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "issuance_period")
    private Date issuancePeriod;

    @Column(name = "number_of_books_given_out")
    private int numberOfBooksGivenOut;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_readers")
    private Readers readers;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_books")
    private Books books;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Date getDateOfIssue() {return dateOfIssue;}

    public void setDateOfIssue(Date dateOfIssue) {this.dateOfIssue = dateOfIssue;}

    public Date getIssuancePeriod() {return issuancePeriod;}

    public void setIssuancePeriod(Date issuancePeriod) {this.issuancePeriod = issuancePeriod;}

    public int getNumberOfBooksGivenOut() {return numberOfBooksGivenOut;}

    public void setNumberOfBooksGivenOut(int numberOfBooksGivenOut) {this.numberOfBooksGivenOut = numberOfBooksGivenOut;}

    public Readers getReaders() {return readers;}

    public void setReaders(Readers readers) {this.readers = readers;}

    public Books getBooks() {return books;}

    public void setBooks(Books books) {this.books = books;}
}
