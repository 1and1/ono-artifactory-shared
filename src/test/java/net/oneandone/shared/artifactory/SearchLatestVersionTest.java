/*
 * Copyright 2013 1&1.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author mifr
 */
public class SearchLatestVersionTest {
    
    /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearch() throws IOException {
        //http://mamrepo.united.domain/artifactory/api/search/latestVersion?g=commons-logging&a=commons-logging&repos=repo1
        HttpClient mockClient = Mockito.mock(HttpClient.class);
        Mockito.when(mockClient.execute(Mockito.any(HttpUriRequest.class), Mockito.any(BasicResponseHandler.class))).thenReturn("1.1.1");
        SearchLatestVersion instance = new SearchLatestVersion(mockClient, URI.create("http://localhost:8081/artifactory/"));
        String expResult = "1.1.1";
        String result = instance.search("repo1", "commons-logging", "commons-logging");
        assertEquals(expResult, result);
    }

    /**
     * Test of buildSearchUri method, of class SearchLatestVersion.
     */
    @Test
    public void testBuildSearchUri() {
        SearchLatestVersion sut = new SearchLatestVersion(null, URI.create("http://localhost:8081/artifactory/"));
        URI expResult = URI.create("http://localhost:8081/artifactory/api/search/latestVersion?repos=repo1&g=commons-logging&a=commons-logging");
        URI result = sut.buildSearchUri("repo1", "commons-logging", "commons-logging");
        assertEquals(expResult, result);
    }
    
    
}