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

import java.io.IOException;
import java.net.URI;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
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

    /**
     * Searches for the latest version.
     *
     * @param repositoryName to search in.
     * @param groupId of the artifact.
     * @param artifactId of the artifact.
     * @return the latest version of the artifact in repositoryName.
     *
     * @throws IOException during remote call.
     */
    public String search(String repositoryName, String groupId, String artifactId) throws NotFoundException {
        final URI searchUri = buildSearchUri(repositoryName, groupId, artifactId);
        final HttpGet get = new HttpGet(searchUri);
        try {
            return client.execute(get, new BasicResponseHandler());
        } catch (HttpResponseException e) {
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                throw new NotFoundException(searchUri);
            } else {
                throw new RuntimeException("searchUri=" + searchUri.toString(), e);
            }
        } catch (IOException e) {
            throw new RuntimeException("searchUri=" + searchUri.toString(), e);
        }
    }

    /**
     * Builds searchUri.
     * @param repositoryName to search in.
     * @param groupId of the artifact.
     * @param artifactId of the artifact.
     * @return searchUri.
     */
    URI buildSearchUri(String repositoryName, String groupId, String artifactId) {
        final URIBuilder uriBuilder = new URIBuilder(baseUri.resolve("api/search/latestVersion"))
                .addParameter("repos", repositoryName)
                .addParameter("g", groupId)
                .addParameter("a", artifactId);
        return Utils.toUri(
                uriBuilder,
                "Could not build uri for " + repositoryName + " with g=" + groupId + ", a=" + artifactId);
    }
}
