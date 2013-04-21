/**
 * Copyright 1&1 Internet AG, https://github.com/1and1/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
