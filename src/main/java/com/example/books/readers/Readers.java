package com.example.books.readers;

import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.returnOfBooks.ReturnOfBooks;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "readers")
public class Readers {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "readers_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "readers_id_seq"),
                    @Parameter(name= "INCREMENT", value = "1"),
                    @Parameter(name = "MINVALUE", value = "1"),
                    @Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "readers_id_seq")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "passport_data")
    private String passportData;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @OneToMany(mappedBy = "readers", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<LoanOfBooks> loanOfBooks = new HashSet<>();

    @OneToMany(mappedBy = "readers", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<ReturnOfBooks> returnOfBooks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LoanOfBooks> getLoanOfBooks() {return loanOfBooks;}

    public void setLoanOfBooks(Set<LoanOfBooks> loanOfBooks) {this.loanOfBooks = loanOfBooks;}

    public Set<ReturnOfBooks> getReturnOfBooks() {return returnOfBooks;}

    public void setReturnOfBooks(Set<ReturnOfBooks> returnOfBooks) {this.returnOfBooks = returnOfBooks;}
}
