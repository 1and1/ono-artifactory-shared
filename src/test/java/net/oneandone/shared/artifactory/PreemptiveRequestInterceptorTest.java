/*
 * Copyright 2013 1&1.
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
