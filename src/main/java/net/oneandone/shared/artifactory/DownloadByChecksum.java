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
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

/**
 *
 * @author Mirko Friedenhagen <mirko.friedenhagen@1und1.de>
 */
public class DownloadByChecksum {

    private final HttpClient client;
    private final FetchStorageByChecksum fetchStorageByChecksum;

    @Inject
    public DownloadByChecksum(@Named("httpclient") HttpClient client,
            @Named("fetchStorageByChecksum") final FetchStorageByChecksum fetchStorageByChecksum) {
        this.client = client;
        this.fetchStorageByChecksum = fetchStorageByChecksum;
    }

    public String download(final String repositoryName, final Sha1 sha1) throws IOException, NotFoundException, URISyntaxException {
        final ArtifactoryStorage storage = fetchStorageByChecksum.fetch(repositoryName, sha1);
        final HttpGet httpGet = new HttpGet(storage.downloadUri.toURI());
        final Sha1 storageSha1 = storage.checksums.sha1;
        if (!sha1.equals(storageSha1)) {
            throw new IllegalStateException("Given sha1=" + sha1 + " does not equal storageSha1=" + storageSha1);
        }
        final BasicResponseHandler responseHandler = new BasicResponseHandlerImpl(storageSha1);
        return client.execute(httpGet, responseHandler);
    }

    private static class BasicResponseHandlerImpl extends BasicResponseHandler {

        private final Sha1 storageSha1;
        public BasicResponseHandlerImpl(Sha1 storageSha1) {
            this.storageSha1 = storageSha1;
        }

        @Override
        public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
            final String body = super.handleResponse(response);
            final Sha1 sha1Header = Sha1.valueOf(response.getFirstHeader("X-Checksum-Sha1").getValue());
            if (!sha1Header.equals(storageSha1)) {
                throw new IllegalStateException("Returned sha1Header=" + sha1Header + " does not equal storageSha1=" + storageSha1);
            }
//TODO: body hash does not equal the given one, encoding??
//                final HashCode bodySha1 = Hashing.sha1().hashString(body);
//                if (!bodySha1.toString().equals(storageSha1)) {
//                    throw new IllegalStateException("Returned bodySha1=" + bodySha1 + " does not equal storageSha1=" + storageSha1);
//                }
            return body;
        }
    }
}
