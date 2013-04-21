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

import net.oneandone.shared.artifactory.model.Gav;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import net.oneandone.shared.artifactory.model.ArtifactoryResults;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Sha1;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author Mirko Friedenhagen
 */
public class SearchByGav {

    private final HttpClient client;
    private final URI baseUri;

    @Inject
    public SearchByGav(@Named("httpclient") HttpClient client, @Named("baseUri") URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    public List<ArtifactoryStorage> search(final String repositoryName, final Gav gav) throws IOException, NotFoundException {
        final URI build = buildSearchURI(repositoryName, gav);
        final HttpGet get = new HttpGet(build);
        get.addHeader("X-Result-Detail", "info");
        final ArtifactoryResults results = client.execute(get, 
                new JsonResponseHandler<ArtifactoryResults>(ArtifactoryResults.class));
        if (results.results.isEmpty()) {
            throw new NotFoundException(build);
        }
        return results.results;
    }

    URI buildSearchURI(String repositoryName, Gav gav) {
        final URIBuilder uriBuilder = new URIBuilder(baseUri.resolve("api/search/gavc"))
                .addParameter("repos", repositoryName)
                .addParameter("g", gav.getGroupId())
                .addParameter("a", gav.getArtifactId())
                .addParameter("v", gav.getVersion());
        return Utils.toUri(uriBuilder,
                "Could not build URI from " + baseUri + " using repositoryName=" + repositoryName + ", gav=" + gav);
    }

}
