package com.sjsu.gofcar.model.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @ToString
@Builder(toBuilder=true)
@Document(collection="AdminDetails")
public class AdminDetails {
  @Id
  @NotNull
  @NotEmpty
  private String email;

  @NotNull @NotEmpty
  private String password;

  @NotNull @NotEmpty
  private String firstName;

  @NotNull @NotEmpty
  private String lastName;
}
