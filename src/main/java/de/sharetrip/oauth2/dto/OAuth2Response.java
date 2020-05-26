package de.sharetrip.oauth2.dto;

import lombok.Value;

@Value
public class OAuth2Response {

    private String accessToken;

    private int expiresIn;

    private final String tokenType = "Bearer";

}
