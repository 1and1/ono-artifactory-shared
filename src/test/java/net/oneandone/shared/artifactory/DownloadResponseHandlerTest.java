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


import java.io.ByteArrayInputStream;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.File;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Mirko Friedenhagen <mirko.friedenhagen@1und1.de>
 */
public class DownloadResponseHandlerTest {

    private static final Sha1 SHA1_OF_BOM_TXT = Sha1.valueOf("996e6e154ac44bd0c981f10632385ffd2944826d");

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder(new File("target"));
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    final HttpResponse mockedResponse = mock(HttpResponse.class);

    final HttpEntity mockEntity = mock(HttpEntity.class);

    final BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");

    DownloadResponseHandler sut;

    @Before
    public void createSut() {
        sut = new DownloadResponseHandler(
            SHA1_OF_BOM_TXT, folder.getRoot());
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
    }

    /**
     * Test of handleResponse method, of class DownloadResponseHandler.
     */
    @Test
    public void testHandleResponseValidSha1() throws Exception {
        setupHttpResponse("hallo");
        DownloadResponseHandler instance = new DownloadResponseHandler(
                    Sha1.valueOf("fd4cef7a4e607f1fcc920ad6329a6df2df99a4e8"), folder.getRoot());
        instance.handleResponse(mockedResponse);
        final File outputFile = new File(folder.getRoot(), "foo.jar");
        assertTrue(outputFile + " does not exist.", outputFile.exists());
    }

    /**
     * Test of handleResponse method, of class DownloadResponseHandler.
     */
    @Test
    public void testHandleResponseInvalidSha1() throws Exception {
        setupHttpResponse("hallo1");
        DownloadResponseHandler instance = new DownloadResponseHandler(
                    Sha1.valueOf("fd4cef7a4e607f1fcc920ad6329a6df2df99a4e8"), folder.getRoot());
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("sha1 from file target");
        thrown.expectMessage("does not equal expected sha1");
        instance.handleResponse(mockedResponse);
    }

    /**
     * Test of checkHeaderNotNull method, of class DownloadResponseHandler.
     */
    @Test
    public void testCheckHeaderNotNull() {
        final String headerName = "DOES_NOT_MATTER";
        when(mockedResponse.getFirstHeader(headerName)).thenReturn(new BasicHeader(headerName, "foo"));
        final Header result = sut.checkHeaderNotNull(mockedResponse, headerName);
        assertEquals("foo", result.getValue());
    }
    /**
     * Test of checkHeaderNotNull method, of class DownloadResponseHandler.
     */
    @Test
    public void testCheckHeaderNotNullHeaderNotFound() {
        final String headerName = "DOES_NOT_MATTER";
        when(mockedResponse.getFirstHeader(headerName)).thenReturn(null);
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Expected Header '" + headerName + "' not found!");
        sut.checkHeaderNotNull(mockedResponse, headerName);
    }

    void setupHttpResponse(final String content) throws IllegalStateException, IOException {
        when(mockedResponse.getFirstHeader("X-Checksum-Sha1")).thenReturn(new BasicHeader("X-Checksum-Sha1", "fd4cef7a4e607f1fcc920ad6329a6df2df99a4e8"));
        when(mockedResponse.getFirstHeader("X-Artifactory-Filename")).thenReturn(new BasicHeader("X-Artifactory-Filename", "foo.jar"));
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(content.getBytes()));
        when(mockedResponse.getEntity()).thenReturn(mockEntity);
    }
}
