package de.sharetrip.user.domain;

public enum AuthProvider {
    LOCAL,
    FACEBOOK,
    GOOGLE,
    GITHUB;

    public static AuthProvider findProvider(final String string) {
        for (final AuthProvider provider : AuthProvider.values()) {
            if (string.equalsIgnoreCase(provider.name())) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Authentication provider not specified");
    }

}
