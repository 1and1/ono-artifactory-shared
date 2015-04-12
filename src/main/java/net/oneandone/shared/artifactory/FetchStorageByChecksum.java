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

import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 *
 * @author Mirko Friedenhagen &lt;mirko.friedenhagen@1und1.de&gt;
 */
public class FetchStorageByChecksum {

    private final HttpClient client;
    private final SearchByChecksum searchByChecksum;

    @Inject
    public FetchStorageByChecksum(@Named("httpclient") HttpClient client,
            @Named("searchByChecksum") final SearchByChecksum searchByChecksum) {
        this.client = client;
        this.searchByChecksum = searchByChecksum;
    }

    public ArtifactoryStorage fetch(final String repositoryName, final Sha1 sha1) throws IOException, NotFoundException, URISyntaxException {
        final URL checksumURL = searchByChecksum.search(repositoryName, sha1);
        final JsonResponseHandler<ArtifactoryStorage> handler = new JsonResponseHandler<ArtifactoryStorage>(ArtifactoryStorage.class);
        final HttpGet httpGet = new HttpGet(checksumURL.toURI());
        return client.execute(httpGet, handler);
    }
}
