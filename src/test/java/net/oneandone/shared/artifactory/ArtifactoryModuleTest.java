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
import java.net.URI;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mifr
 */
public class ArtifactoryModuleTest {
    
    private static final String DEFAULT_ARTIFACTORY = "http://localhost:8081/artifactory/";

    /**
     * Test of configure method, of class ArtifactoryModule.
     */
    @Test
    public void testConfigure() {
        Guice.createInjector(new ArtifactoryModule(DEFAULT_ARTIFACTORY));
    }

    /**
     * Test of provideHttpClient method, of class ArtifactoryModule.
     */
    @Test
    public void testProvideHttpClient() {
        ArtifactoryModule sut = new ArtifactoryModule(DEFAULT_ARTIFACTORY);
        assertNotNull(sut.provideHttpClient());
    }

    /**
     * Test of provideUri method, of class ArtifactoryModule.
     */
    @Test
    public void testProvideUri() {
        ArtifactoryModule sut = new ArtifactoryModule(DEFAULT_ARTIFACTORY);
        URI expResult = URI.create(DEFAULT_ARTIFACTORY);
        URI result = sut.provideUri();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArtifactoryUrl method, of class ArtifactoryModule.
     */
    @Test
    public void testGetArtifactoryUrl() {
        ArtifactoryModule sut = new ArtifactoryModule(DEFAULT_ARTIFACTORY);
        String result = sut.getArtifactoryUrl();
        assertEquals(DEFAULT_ARTIFACTORY, result);
    }
    
}