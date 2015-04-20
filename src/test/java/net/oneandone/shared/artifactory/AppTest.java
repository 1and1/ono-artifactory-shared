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

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.oneandone.shared.artifactory.model.ArtifactoryChecksumResults;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.MD5;
import net.oneandone.shared.artifactory.model.Sha1;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(parse.results.get(0).uri.toString()).isEqualTo("http://localhost:8081/artifactory/api/storage/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar");
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
        assertThat(parse.downloadUri.toString()).isEqualTo(
            "http://localhost:8081/artifactory/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar");
        assertThat(parse.metadataUri.toString()).isEqualTo("http://localhost:8081/artifactory/api/storage/plugins-snapshot-local/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar?mdns");
        assertThat(parse.createdBy).isEqualTo("mirko");
        assertThat(parse.modifiedBy).isEqualTo("mirko");
        final Sha1 expectedSha1 = Sha1.valueOf("2dcd8da8155d9b87dc9569c3b4b1451e9d888aaf");
        assertThat(parse.checksums.sha1).isEqualTo(expectedSha1);
        assertThat(parse.originalChecksums.sha1).isEqualTo(expectedSha1);
        assertThat(parse.mimeType).isEqualTo("application/java-archive");
        assertThat(parse.size).isEqualTo(59122L);
        assertThat(parse.created).isEqualTo(new DateTime("2012-10-19T20:52:28.120+02:00"));
        assertThat(parse.lastModified).isEqualTo(new DateTime("2012-10-19T20:52:28.119+02:00"));
        assertThat(parse.lastUpdated).isEqualTo(new DateTime("2012-10-19T20:52:28.121+02:00"));
        assertThat(parse.repo).isEqualTo("plugins-snapshot-local");
        assertThat(parse.path).isEqualTo("/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.1-SNAPSHOT/bill-of-materials-maven-plugin-2.1-SNAPSHOT-javadoc.jar");
    }
}
