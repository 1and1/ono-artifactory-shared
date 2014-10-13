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

import com.google.inject.Guice;
import com.google.inject.Injector;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mifr
 */
public class SearchLatestVersionIT {


    private final ArtifactoryITModule artifactoryITModule = new ArtifactoryITModule();
    private final Injector injector = Guice.createInjector(artifactoryITModule);
    private final SearchLatestVersion sut = injector.getInstance(SearchLatestVersion.class);

    @Before
    public void checkOSS() {
        artifactoryITModule.needNonOSS();
    }

   /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test
    public void testSearch() throws NotFoundException {
        String expResult = "1.1.3";
        String result = sut.search("repo1", "commons-logging", "commons-logging");
        assertEquals(expResult, result);
    }
   /**
     * Test of search method, of class SearchLatestVersion.
     */
    @Test(expected = NotFoundException.class)
    public void testSearchNotFound() throws NotFoundException {
        sut.search("repo1", "commons-logging", "commons-logginggggggggggg");
    }
}
