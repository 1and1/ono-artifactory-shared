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
import java.util.List;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Gav;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mirko Friedenhagen.
 */
public class SearchByGavIT {

    final SearchByGav sut;
    final String repositoryName = "repo1-cache";
    final Gav gav = new Gav("junit", "junit", "4.11");

    public SearchByGavIT() {
        Injector injector = Guice.createInjector(new ArtifactoryModule());
        sut = injector.getInstance(SearchByGav.class);
    }

    /**
     * Test of search method, of class SearchByGav.
     */
    @Test
    public void testSearch() throws Exception {
        List<ArtifactoryStorage> result = sut.search(repositoryName, gav);
        assertTrue("Have at leat 4 resources", result.size() >= 4);
    }
}
