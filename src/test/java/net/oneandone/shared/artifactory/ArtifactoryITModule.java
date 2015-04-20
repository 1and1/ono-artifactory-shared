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

import net.oneandone.shared.artifactory.model.Version;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.AssumptionViolatedException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Mirko Friedenhagen
 */
public class ArtifactoryITModule extends ArtifactoryModule {

    private final Version version;

    public ArtifactoryITModule() {
        super();
        try {
            final URL url = new URL(getArtifactoryUrl() + "api/system/version");
            final JsonResponseHandler<Version> versionJsonResponseHandler = new JsonResponseHandler<>(Version.class);
            try {
                final HttpClient client = provideHttpClient();
                final HttpGet get = new HttpGet(url.toURI());
                version = client.execute(get, versionJsonResponseHandler);
            } catch (IOException ex) {
                throw new AssumptionViolatedException("Could not reach " + url);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException ex) {
            throw new AssumptionViolatedException("Malformed URL", ex);
        }
    }

    public void needNonOSS() {
        if (version.license.equals("Artifactory OSS")) {
            throw new AssumptionViolatedException("Does not run with Artifactory OSS.");
        }
    }
}
