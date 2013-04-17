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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import java.net.URI;
import javax.inject.Named;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mirko Friedenhagen
 */
public class ArtifactoryModule extends AbstractModule {

    private final static Logger LOG = LoggerFactory.getLogger(ArtifactoryModule.class);
    private static final String DEFAULT_ARTIFACTORY = "http://localhost:8081/artifactory/";

    private final String artifactoryUrl;

    public ArtifactoryModule(String artifactoryUrl) {
        this.artifactoryUrl = artifactoryUrl != null ? artifactoryUrl : DEFAULT_ARTIFACTORY;
        LOG.debug("artifactoryUrl={}", this.artifactoryUrl);
    }

    ArtifactoryModule() {
        this(System.getenv("ARTIFACTORY_INSTANCE"));
    }

    @Override
    protected void configure() {
        bind(SearchByChecksum.class).annotatedWith(Names.named("searchByChecksum")).to(SearchByChecksum.class);
        bind(FetchStorageByChecksum.class).annotatedWith(Names.named("fetchStorageByChecksum")).to(FetchStorageByChecksum.class);
        bind(SearchLatestVersion.class).annotatedWith(Names.named("searchLatestVersion")).to(SearchLatestVersion.class);
    }

    @Provides
    @Named(value = "httpclient")
    HttpClient provideHttpClient() {
        final DefaultHttpClient client = new DefaultHttpClient();
        client.addRequestInterceptor(new RequestAcceptEncoding());
        client.addResponseInterceptor(new ResponseContentEncoding());
        return client;
    }

    @Provides
    @Named(value = "baseUri")
    URI provideUri() {
        return URI.create(getArtifactoryUrl());
    }

    /**
     * @return the artifactoryUrl
     */
    public String getArtifactoryUrl() {
        return artifactoryUrl;
    }
}
