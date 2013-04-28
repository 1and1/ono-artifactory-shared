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

import net.oneandone.shared.artifactory.DateTimeDeserializer;
import net.oneandone.shared.artifactory.MD5Deserializer;
import net.oneandone.shared.artifactory.Sha1Deserializer;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.MD5;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
            .registerTypeAdapter(Sha1.class, new Sha1Deserializer())
            .registerTypeAdapter(MD5.class, new MD5Deserializer())
            .create();

    @Test
    public void checkSearchResult() throws IOException {
        final InputStream resourceAsStream = AppTest.class.getResourceAsStream("/checksum.json");        
        final ArtifactoryChecksumResults parse;
        try {
            parse = gson.fromJson(new InputStreamReader(resourceAsStream, Charsets.UTF_8), ArtifactoryChecksumResults.class);
        }
        finally {
            resourceAsStream.close();
        }
        assertEquals("http://localhost:8081/artifactory/api/storage/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar", parse.results.get(0).uri.toString());
    }

    @Test
    public void checkStorageResult() throws IOException {
        final InputStream resourceAsStream = AppTest.class.getResourceAsStream("/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc-storage.json");
        final ArtifactoryStorage parse;
        try {
            parse = gson.fromJson(new InputStreamReader(resourceAsStream, Charsets.UTF_8), ArtifactoryStorage.class);
        }
        finally {
            resourceAsStream.close();
        }
        assertEquals(
            "http://localhost:8081/artifactory/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar", parse.downloadUri.toString());
        assertEquals("http://localhost:8081/artifactory/api/storage/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar?mdns", parse.metadataUri.toString());
        assertEquals("mirko", parse.createdBy);
        assertEquals("mirko", parse.modifiedBy);
        final Sha1 expectedSha1 = Sha1.valueOf("2dcd8da8155d9b87dc9569c3b4b1451e9d888aaf");
        assertEquals(expectedSha1, parse.checksums.sha1);
        assertEquals(expectedSha1, parse.originalChecksums.sha1);
        assertEquals("application/java-archive", parse.mimeType);
        assertEquals(59122L, parse.size);
        assertEquals(new DateTime("2012-10-19T20:52:28.120+02:00"), parse.created);
        assertEquals(new DateTime("2012-10-19T20:52:28.119+02:00"), parse.lastModified);
        assertEquals(new DateTime("2012-10-19T20:52:28.121+02:00"), parse.lastUpdated);
        assertEquals("plugins-snapshot-local", parse.repo);
        assertEquals("/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar", parse.path);
    }
}
