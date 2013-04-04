/*
 * Copyright 2012 1&1.
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


import net.oneandone.shared.artifactory.DownloadResponseHandler;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.File;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

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
    @Ignore
    @Test
    public void testHandleResponse() throws Exception {
        System.out.println("handleResponse");
        HttpResponse response = null;
        DownloadResponseHandler instance = new DownloadResponseHandler(
                    Sha1.valueOf("b8f3661de93691420f65d799f490e53305af8c40"), folder.getRoot());
        Void result = instance.handleResponse(response);
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
}
