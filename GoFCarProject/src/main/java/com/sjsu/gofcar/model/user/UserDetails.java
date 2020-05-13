package com.sjsu.gofcar.model.user;

import com.sjsu.gofcar.model.Address;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @ToString
@Builder(toBuilder=true)
@Document(collection="UserDetails")
public class UserDetails {
    @Id
    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private String firstName;

    @NotNull @NotEmpty
    private String lastName;

    @NotNull @NotEmpty
    private Integer age;

    @NotNull @NotEmpty
    private Address address;

    @NotNull @NotEmpty
    private Boolean isActive;

    @NotNull @NotEmpty
    private License license;

    @NotNull @NotEmpty
    private CreditCard card;

    private Long validity;

    private Long membershipFee;
}
