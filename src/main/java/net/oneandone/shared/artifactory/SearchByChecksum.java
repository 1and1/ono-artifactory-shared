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
import java.net.URISyntaxException;
import java.net.URL;
import javax.inject.Inject;
import javax.inject.Named;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import net.oneandone.shared.artifactory.model.Sha1;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author Mirko Friedenhagen
 */
public class SearchByChecksum {
    
    private final HttpClient client;
    private final URI baseUri;

    @Inject
    public SearchByChecksum(@Named("httpclient") HttpClient client, @Named("baseUri") URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }
    
    public URL search(final String repositoryName, final Sha1 sha1) throws IOException, NotFoundException {
        final URI build = buildSearchURI(repositoryName, sha1);
        final HttpGet shaGet = new HttpGet(build);
        final ArtifactoryChecksumResults checksumResults = client.execute(shaGet, new JsonResponseHandler<ArtifactoryChecksumResults>(ArtifactoryChecksumResults.class));
        if (checksumResults.results.isEmpty()) {
            throw new NotFoundException("Could not find results using repositoryName=" + repositoryName + ", sha1=" + sha1 + " at " + baseUri);
        }
        return checksumResults.results.get(0).uri;
    }

    URI buildSearchURI(final String repositoryName, final Sha1 sha1) throws IllegalArgumentException {
        final URI searchUri;
        try {
            searchUri = new URIBuilder(baseUri.resolve("api/search/checksum"))
                    .addParameter("repos", repositoryName)
                    .addParameter("sha1", sha1.toString()).build();
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Could not build URI using repositoryName=" + repositoryName + ", sha1=" + sha1, ex);
        }
        return searchUri;
    }    
}
