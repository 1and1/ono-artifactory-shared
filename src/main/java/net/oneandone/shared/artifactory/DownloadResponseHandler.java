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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import com.google.common.io.ByteStreams;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author Mirko Friedenhagen <mirko.friedenhagen@1und1.de>
 */
public class DownloadResponseHandler extends StatusCodeCodeLessThanScMultipleChoicesResponseHandler<Void> {

    public static final String X_ARTIFACTORY_FILENAME = "X-Artifactory-Filename";

    static final String X_CHECKSUM_SHA1 = "X-Checksum-Sha1";
    
    private final Sha1 expectedSha1;
    
    private final File targetDirectory;

    public DownloadResponseHandler(final Sha1 expectedSha1, final File targetDirectory) {
        this.expectedSha1 = expectedSha1;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public Void handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        final HttpEntity entity = returnEntityWhenStatusValid(response);
        final Header sha1Header = checkHeaderNotNull(response, X_CHECKSUM_SHA1);        
        final Header fileNameHeader = checkHeaderNotNull(response, X_ARTIFACTORY_FILENAME);
        final Sha1 sha1FromHeader = Sha1.valueOf(sha1Header.getValue());
        checkState(
                sha1FromHeader.equals(expectedSha1),
                "sha1 from header %s (%s) does not equal expected sha1 (%s)", X_CHECKSUM_SHA1, sha1FromHeader, expectedSha1);
        
        final File outputFile = new File(targetDirectory, fileNameHeader.getValue());
        final Sha1FilterOutputStream sha1FilterOutputStream = new Sha1FilterOutputStream(
                new BufferedOutputStream(new FileOutputStream(outputFile)));
        try {
            ByteStreams.copy(entity.getContent(), sha1FilterOutputStream);
        } finally {
            sha1FilterOutputStream.close();
        }
        final Sha1 sha1OfOutputFile = sha1FilterOutputStream.getSha1();
        checkState(
                sha1OfOutputFile.equals(expectedSha1),
                "sha1 from file %s (%s) does not equal expected sha1 (%s)", outputFile, sha1OfOutputFile, expectedSha1);
        return null;
    }

    Header checkHeaderNotNull(HttpResponse response, final String headerName) {
        return checkNotNull(
                response.getFirstHeader(headerName), "Expected Header '" + headerName + "' not found!");
    }

}
