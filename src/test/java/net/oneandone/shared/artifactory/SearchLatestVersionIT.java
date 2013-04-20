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

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

/**
 *
 * @author mifr
 */
public class SearchLatestVersionIT {

    final SearchLatestVersion searchLatestVersion;

    public SearchLatestVersionIT() {
        Injector injector = Guice.createInjector(new ArtifactoryModule());
        searchLatestVersion = injector.getInstance(SearchLatestVersion.class);
    }

   /**
     * Test of get method, of class SearchLatestVersion.
     */
    @Test
    public void testGet() throws IOException {
        assumeThat("ARTIFACTORY_INSTANCE not set, skipping.", System.getenv("ARTIFACTORY_INSTANCE"), notNullValue());
        String expResult = "1.1.2";
        String result = searchLatestVersion.get("repo1", "commons-logging", "commons-logging");
        assertEquals(expResult, result);
    }
}