/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory;

import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 * {@link HttpRequestInterceptor} which adds preemptively basic auth credentials depending on the host.
 *
 * @author Mirko Friedenhagen
 */
public class PreemptiveRequestInterceptor implements HttpRequestInterceptor {
    static final String AUTHORIZATION_HEADER = "Authorization";

    /** Holds the credentials for hosts. */
    private final HashMap<String, UsernamePasswordCredentials> credentialsMap =
            new HashMap<String, UsernamePasswordCredentials>();

    /**
     * Adds userName and password for one host.
     *
     * @param hostName for which userName and password are added .
     * @param userName for the host.
     * @param password for the host.
     */
    public void addCredentialsForHost(String hostName, String userName, String password) {
        credentialsMap.put(hostName, new UsernamePasswordCredentials(userName, password));
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        final HttpHost httpHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        final UsernamePasswordCredentials credentials = credentialsMap.get(httpHost.toHostString());
        if (credentials != null) {
            final String auth = credentials.getUserName() + ":" + credentials.getPassword();
            request.addHeader(AUTHORIZATION_HEADER, "Basic " + BaseEncoding.base64().encode(auth.getBytes()));
        }
    }

}
