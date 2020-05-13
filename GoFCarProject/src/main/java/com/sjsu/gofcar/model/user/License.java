package com.sjsu.gofcar.model.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data @ToString
@Builder(toBuilder=true)
public class License {
    private String licenseId;
    private Date expiry;
    private String type;
    private String stateOfIssue;
}
