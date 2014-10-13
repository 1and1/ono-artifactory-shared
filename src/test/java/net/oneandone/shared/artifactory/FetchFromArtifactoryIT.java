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
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mirko Friedenhagen
 */
public class FetchFromArtifactoryIT {

    final ArtifactoryITModule artifactoryModule = new ArtifactoryITModule();
    public static final int BOM_JAVADOC_SIZE = 45966;

    final Injector injector = Guice.createInjector(artifactoryModule);
    final SearchByChecksum sutSearchByChecksum = injector.getInstance(SearchByChecksum.class);
    final DownloadByChecksum sutDownloadByChecksum = injector.getInstance(DownloadByChecksum.class);
    final FetchStorageByChecksum sutFetchStorageByChecksum = injector.getInstance(FetchStorageByChecksum.class);
    final Sha1 sha1OfBomJavaDoc = Sha1.valueOf("d70e4ec32cf9ee8124ceec983147efc361153180");

    @Before
    public void checkOSS() {
        artifactoryModule.needNonOSS();
    }
    @Test
    public void searchByChecksum() throws IOException, NotFoundException {        
        final URL checksumURL = sutSearchByChecksum.search(
                "repo1",
                sha1OfBomJavaDoc);
        assertEquals(artifactoryModule.getArtifactoryUrl() + "api/storage/repo1/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.0/bill-of-materials-maven-plugin-2.0-javadoc.jar",
                checksumURL.toString());
    }
    
    @Test
    public void storageByChecksum() throws IOException, NotFoundException, URISyntaxException {
        final ArtifactoryStorage storage = sutFetchStorageByChecksum.fetch(
                "repo1",
                sha1OfBomJavaDoc);
        assertEquals(sha1OfBomJavaDoc, storage.checksums.sha1);
        assertEquals(BOM_JAVADOC_SIZE, storage.size);
        assertEquals(artifactoryModule.getArtifactoryUrl() + "repo1/net/oneandone/maven/plugins/bill-of-materials-maven-plugin/2.0/bill-of-materials-maven-plugin-2.0-javadoc.jar",
                storage.downloadUri.toString());
    }

    @Test
    public void downloadByChecksum() throws IOException, NotFoundException, URISyntaxException {
        String download = sutDownloadByChecksum.download(
                "repo1",
                sha1OfBomJavaDoc);
        assertEquals(BOM_JAVADOC_SIZE, download.length());

    }

    @Test(expected=NotFoundException.class)
    public void searchByChecksumNotFound() throws IOException, NotFoundException {
        sutSearchByChecksum.search(
                "libs-release-local",
                Sha1.valueOf("d70e4ec32cf9ee8124ceec983147e00000000000"));
    }
}
