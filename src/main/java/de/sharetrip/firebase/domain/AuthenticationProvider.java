package de.sharetrip.firebase.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthenticationProvider {
    FACEBOOK("facebook.com"),
    GOOGLE("google.com");

    private final String signInProvider;

    public static AuthenticationProvider findBySignInProvider(final String signInProvider) {

        for (final AuthenticationProvider provider : AuthenticationProvider.values()) {
            if (provider.signInProvider.equals(signInProvider)) {
                return provider;
            }
        }
        throw new IllegalArgumentException(String.format("Illegal sign in provider [%s]", signInProvider));
    }
}
