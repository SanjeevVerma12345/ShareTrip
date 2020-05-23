package de.sharetrip.core.payload;

import lombok.Data;

@Data
public class AuthResponse {

    private final String accessToken;

    private final static String TOKEN_TYPE = "Bearer";

    public AuthResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

}
