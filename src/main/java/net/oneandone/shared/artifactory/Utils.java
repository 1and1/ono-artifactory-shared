/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;

/**
 * Helper class with uncoverable methods.
 *
 * @author Mirko Friedenhagen
 */
public final class Utils {

    private Utils() {
        // class with static helper methods.
    }

    /**
     * Builds the URI, throws an {@link IllegalArgumentException} with errorMessage when something goes wrong.
     *
     * @param uriBuilder we want to convert to an URI.
     * @param errorMessage to include in the IllegalArgumentException.
     * @return the URI.
     */
    public static URI toUri(URIBuilder uriBuilder, String errorMessage) {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(errorMessage, ex);
        }
    }
}
