package de.sharetrip.oauth2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class AuthorizeDto {

    @Email
    private String emailId;

    @NotEmpty
    private String idToken;

}
