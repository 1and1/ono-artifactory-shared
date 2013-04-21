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

import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.List;
import net.oneandone.shared.artifactory.model.ArtifactoryResults;
import net.oneandone.shared.artifactory.model.ArtifactoryResultsTest;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Gav;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 *
 * @author mirko
 */
public class SearchByGavTest {

    final HttpClient mockClient = mock(HttpClient.class);
    final SearchByGav sut = new SearchByGav(mockClient, URI.create("http://localhost/"));
    final String repositoryName = "repo1";
    final Gav gav = new Gav("junit", "junit", "4.11");

    /**
     * Test of search method, of class SearchByGav.
     */
    @Test
    public void testSearch() throws Exception {
        final String resourceName = "/junit-4.11-storage.json";
        final InputStreamReader reader = new InputStreamReader(
                SearchByGavTest.class.getResourceAsStream(resourceName));
        ArtifactoryResults staticResults;
        try {
            staticResults = Utils.createGson().fromJson(reader, ArtifactoryResults.class);
        } finally {
            reader.close();
        }
        when(mockClient.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(staticResults);
        List<ArtifactoryStorage> result = sut.search(repositoryName, gav);
        assertEquals(4, result.size());
    }

    /**
     * Test of buildSearchURI method, of class SearchByGav.
     */
    @Test
    public void testBuildSearchURI() {
        URI expResult = URI.create("http://localhost/api/search/gavc?repos=repo1&g=junit&a=junit&v=4.11");
        URI result = sut.buildSearchURI(repositoryName, gav);
        assertEquals(expResult, result);
    }
}
