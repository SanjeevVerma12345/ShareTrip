package de.sharetrip.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private String emailId;

    private boolean locked;

    private UserDetailsDto userDetails;
}
