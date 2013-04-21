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
package net.oneandone.shared.artifactory.model;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import net.oneandone.shared.artifactory.Utils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mirko
 */
public class ArtifactoryResultsTest {
    
    @Test
    public void checkDeserializationOfStorageData() throws IOException {
        final String resourceName = "/junit-4.11-storage.json";
        final InputStreamReader reader = new InputStreamReader(
                ArtifactoryResultsTest.class.getResourceAsStream(resourceName));
        List<ArtifactoryStorage> results;
        try {
            results = Utils.createGson().fromJson(reader, ArtifactoryResults.class).results;
        } finally {
            reader.close();
        }
        assertEquals(4, results.size());
        final ArtifactoryStorage pomInfo = results.get(0);
        assertEquals("anonymous", pomInfo.createdBy);
        assertEquals(Sha1.valueOf("cddf7490ffe839978cf5d6c944c01f2a8cb70a49"), pomInfo.checksums.sha1);
    }
    
}
