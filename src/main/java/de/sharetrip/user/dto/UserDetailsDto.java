package de.sharetrip.user.dto;

import de.sharetrip.core.dto.CountryDto;
import de.sharetrip.core.dto.ImageDto;
import lombok.Data;

@Data
public class UserDetailsDto {

    private String firstName;

    private String lastName;

    private Integer age;

    private String gender;

    private ImageDto userImage;

    private String aboutMe;

    private CountryDto country;
}
