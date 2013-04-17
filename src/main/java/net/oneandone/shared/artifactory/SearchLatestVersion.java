/*
 * Copyright 2013 1&1.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oneandone.shared.artifactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;

/**
 * Retrieves the latest version of an artifact for a specified repository from Artifactory.
 *
 * @author Mirko Friedenhagen
 */
public class SearchLatestVersion {
    private final URI baseUri;
    private final HttpClient client;

    @Inject
    public SearchLatestVersion(@Named("httpclient")HttpClient client, @Named("baseUri")URI baseUri) {
        this.baseUri = baseUri;
        this.client = client;
    }
    
    public String get(String repositoryId, String groupId, String artifactId) throws IOException {
        final URI build = createSearchUri(repositoryId, groupId, artifactId);
        HttpGet get = new HttpGet(build);
        return client.execute(get, new BasicResponseHandler());
    }

    URI createSearchUri(String repositoryId, String groupId, String artifactId) {
        try {
            return new URIBuilder(baseUri.resolve("api/search/latestVersion"))
                    .addParameter("repos", repositoryId)
                    .addParameter("g", groupId)
                    .addParameter("a", artifactId).build();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
