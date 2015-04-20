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

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

/**
 *
 * @author mifr
 */
public class SearchLatestVersionTest {

    final HttpClient mockClient = mock(HttpClient.class);

    final SearchLatestVersion sut = new SearchLatestVersion(mockClient, URI.create("http://localhost:8081/artifactory/"));

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearch() throws NotFoundException, IOException {
        setupValidSearch();
        String expResult = "1.1.1";
        String result = sut.search("repo1", "commons-logging", "commons-logging");
        assertThat(result).isEqualTo(expResult);
    }

    /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearchNotfound() throws NotFoundException, IOException {
        final HttpResponseException notFound = new HttpResponseException(HttpStatus.SC_NOT_FOUND, "Not found");
        setupSearchWithException(notFound);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("No results found");
        sut.search("repo1", "commons-logging", "commons-logging");
    }

    /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearchOtherHttpResponseException() throws NotFoundException, IOException {
        final HttpResponseException badRequest = new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "Bad Request");
        setupSearchWithException(badRequest);
        thrown.expect(RuntimeException.class);
        thrown.expectCause(is(badRequest));
        sut.search("repo1", "commons-logging", "commons-logging");
    }

    /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearchIOException() throws NotFoundException, IOException {
        final IOException ex = new IOException("foo");
        setupSearchWithException(ex);
        thrown.expect(RuntimeException.class);
        thrown.expectCause(is(ex));
        sut.search("repo1", "commons-logging", "commons-logging");
    }

    /**
     * Test of buildSearchUri method, of class SearchLatestVersion.
     */
    @Test
    public void testBuildSearchUri() {
        URI expResult = URI.create("http://localhost:8081/artifactory/api/search/latestVersion?repos=repo1&g=commons-logging&a=commons-logging");
        URI result = sut.buildSearchUri("repo1", "commons-logging", "commons-logging");
        assertThat(result).isEqualTo(expResult);
    }

    void setupValidSearch() throws IOException {
        //http://mamrepo.united.domain/artifactory/api/search/latestVersion?g=commons-logging&a=commons-logging&repos=repo1
        when(mockClient.execute(any(HttpUriRequest.class), any(BasicResponseHandler.class))).thenReturn("1.1.1");
    }

    void setupSearchWithException(final Exception thrownException) throws IOException {
        when(mockClient.execute(
                any(HttpUriRequest.class),
                any(BasicResponseHandler.class))).thenThrow(thrownException);
    }


}
