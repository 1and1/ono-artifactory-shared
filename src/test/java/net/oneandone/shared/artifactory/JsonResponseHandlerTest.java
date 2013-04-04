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

import net.oneandone.shared.artifactory.JsonResponseHandler;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResult;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpResponseException;
import org.apache.http.message.BasicStatusLine;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Mirko Friedenhagen
 */
public class JsonResponseHandlerTest {

    final JsonResponseHandler<ArtifactoryChecksumResults> sut = new JsonResponseHandler<ArtifactoryChecksumResults>(ArtifactoryChecksumResults.class);
    final HttpResponse mockedResponse = mock(HttpResponse.class);

    /**
     * Test of handleResponse method, of class JsonResponseHandler.
     */
    @Test(expected = HttpResponseException.class)
    public void testHandleResponseBadStatusCode() throws Exception {
        final BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_MULTIPLE_CHOICES, "Multiple Choices");
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        sut.handleResponse(mockedResponse);
    }

    /**
     * Test of handleResponse method, of class JsonResponseHandler.
     */
    @Test
    public void testHandleResponse() throws Exception {
        final BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        final HttpEntity mockedEntity = mock(HttpEntity.class);
        when(mockedEntity.getContent()).thenReturn(JsonResponseHandlerTest.class.getResourceAsStream("/checksum.json"));
        when(mockedResponse.getEntity()).thenReturn(mockedEntity);
        final ArtifactoryChecksumResults checksumResults = sut.handleResponse(mockedResponse);
        final ArtifactoryChecksumResult checksumResult = checksumResults.results.get(0);
        assertEquals(
                "http://localhost:8081/artifactory/api/storage/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar", 
                checksumResult.uri.toString());
    }
}
