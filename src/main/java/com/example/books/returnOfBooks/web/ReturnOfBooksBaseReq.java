package com.example.books.returnOfBooks.web;

import com.example.books.base.BaseRequest;

import javax.validation.constraints.NotNull;

public class ReturnOfBooksBaseReq extends BaseRequest {
    @NotNull
    private int returnAmount;

    @NotNull
    private Long readersId;

    @NotNull
    private Long extraditionId;

    public int getReturnAmount() {return returnAmount;}

    public void setReturnAmount(int returnAmount) {this.returnAmount = returnAmount;}

    public Long getReadersId() {return readersId;}

    public void setReadersId(Long readersId) {this.readersId = readersId;}

    public Long getExtraditionId() {return extraditionId;}

    public void setExtraditionId(Long extraditionId) {this.extraditionId = extraditionId;}
}
