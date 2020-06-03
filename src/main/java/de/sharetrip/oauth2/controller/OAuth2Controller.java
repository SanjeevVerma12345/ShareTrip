package de.sharetrip.oauth2.controller;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.oauth2.dto.AuthorizeDto;
import de.sharetrip.oauth2.dto.OAuth2Response;
import de.sharetrip.oauth2.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth2/authorize")
public class OAuth2Controller {

    private final OAuthService oAuthService;

    @PostMapping
    public OAuth2Response prepareAccessToken(@Valid @RequestBody final AuthorizeDto authorizeDto)
            throws UserNotAuthorizedException, AccountLockedException {
        return oAuthService.prepareOAuthResponse(authorizeDto);
    }
}
