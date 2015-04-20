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

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Mirko Friedenhagen
 */
public class PreemptiveRequestInterceptorTest {
    final HttpRequest mockRequest = mock(HttpRequest.class);
    final HttpContext mockContext = mock(HttpContext.class);
    final PreemptiveRequestInterceptor sut = new PreemptiveRequestInterceptor();

    /**
     * Test of process method, of class PreemptiveRequestInterceptor.
     */
    @Test
    public void testProcess() throws Exception {
        prepareTargetHostWithUserNameAndPassword();
        sut.process(mockRequest, mockContext);
        verify(mockRequest, times(1)).addHeader(PreemptiveRequestInterceptor.AUTHORIZATION_HEADER, "Basic dXNlcjpwYXNzd29yZA==");
    }

    /**
     * Test of process method, of class PreemptiveRequestInterceptor.
     */
    @Test
    public void testProcessWithoutUserNameAndPassword() throws Exception {
        prepareTargetHost();
        sut.process(mockRequest, mockContext);
        verifyZeroInteractions(mockRequest);
    }

    void prepareTargetHostWithUserNameAndPassword() {
        sut.addCredentialsForHost("webdav.smartdrive.web.de", "user", "password");
        prepareTargetHost();
    }

    void prepareTargetHost() {
        when(mockContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST)).thenReturn(new HttpHost("webdav.smartdrive.web.de"));
    }

}
