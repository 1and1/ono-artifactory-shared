/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import net.oneandone.shared.artifactory.model.MD5;
import net.oneandone.shared.artifactory.model.Sha1;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;

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
    /**
     * Creates as Gson converter for Artifactory storage results.
     *
     * @return gson
     */
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(Sha1.class, new Sha1Deserializer())
                .registerTypeAdapter(MD5.class, new MD5Deserializer()).create();
    }
}
