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

import net.oneandone.shared.artifactory.NotFoundException;
import net.oneandone.shared.artifactory.SearchByChecksum;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResult;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import net.oneandone.shared.artifactory.model.Sha1;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Mirko Friedenhagen
 */
public class SearchByChecksumTest {

    final HttpClient mockedClient = mock(HttpClient.class);
    final SearchByChecksum sut = new SearchByChecksum(mockedClient, URI.create("http://localhost/"));
    final ArtifactoryChecksumResults checksumResults = new ArtifactoryChecksumResults();

    public SearchByChecksumTest() {
        checksumResults.results = new ArrayList<ArtifactoryChecksumResult>();
    }

    /**
     * Test of search method, of class SearchByChecksum.
     */
    @Test
    public void testSearch() throws Exception {
        final ArtifactoryChecksumResult checksumResult = new ArtifactoryChecksumResult();
        final URL expected = new URL("http://localhost/");
        checksumResult.uri = expected;
        checksumResults.results.add(checksumResult);
        when(mockedClient.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(checksumResults);
        URL search = sut.search("foo", Sha1.valueOf("d70e4ec32cf9ee8124ceec983147efc361153180"));
        assertEquals(expected, search);   
    }

    /**
     * Test of search method, of class SearchByChecksum.
     */
    @Test(expected=NotFoundException.class)
    public void testSearchNoResult() throws Exception {
        when(mockedClient.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(checksumResults);
        sut.search("foo", Sha1.valueOf("d70e4ec32cf9ee8124ceec983147efc361153180"));
    }
    
    /**
     * Test of buildSearchURI method, of class SearchByChecksum.
     */
    @Test
    public void testBuildSearchURI() {
        URI buildSearchURI = sut.buildSearchURI("foo", Sha1.valueOf("d70e4ec32cf9ee8124ceec983147efc361153180"));
        assertEquals("http://localhost/api/search/checksum?repos=foo&sha1=d70e4ec32cf9ee8124ceec983147efc361153180",
                buildSearchURI.toString());
    }
}
