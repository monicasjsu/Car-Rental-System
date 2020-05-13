package com.sjsu.gofcar.model.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder=true)
public class CreditCard {

    @NotNull @NotEmpty
    private String cardNumber;

    @NotNull @NotEmpty
    private Date dateOfIssue;

    @NotNull @NotEmpty
    private Date expiryDate;

    @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private Integer cvv;
}
