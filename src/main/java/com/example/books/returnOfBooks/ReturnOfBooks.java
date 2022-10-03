package com.example.books.returnOfBooks;

import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.readers.Readers;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "return_of_books")
public class ReturnOfBooks {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "return_of_books_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "return_of_books_id_seq"),
                    @org.hibernate.annotations.Parameter(name= "INCREMENT", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "return_of_books_id_seq")
    private Long id;

    @Column(name = "return_amount")
    private int returnAmount;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_readers")
    private Readers readers;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_extradition")
    private LoanOfBooks loanOfBooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(int returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Readers getReaders() {
        return readers;
    }

    public void setReaders(Readers readers) {
        this.readers = readers;
    }

    public LoanOfBooks getLoanOfBooks() {
        return loanOfBooks;
    }

    public void setLoanOfBooks(LoanOfBooks loanOfBooks) {
        this.loanOfBooks = loanOfBooks;
    }
}
