package com.sjsu.gofcar.model.user;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data @ToString
@Builder(toBuilder=true)
public class Login {
    @Id
    private String email;
    private String password;
}
