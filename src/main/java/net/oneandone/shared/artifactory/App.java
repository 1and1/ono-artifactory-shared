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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import javax.inject.Inject;
import javax.inject.Named;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Gav;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

    private final static Logger LOG = LoggerFactory.getLogger(App.class);
    
    private final static URI DEFAULT_ARTIFACTORY_URI = URI.create("http://localhost:8081/artifactory/");

    private final PreemptiveRequestInterceptor preemptiveRequestInterceptor;
    
    private static void initLogging() {
        final InputStream resourceAsStream = App.class.getResourceAsStream("/logging.properties");
        try {
            try {
                LogManager.getLogManager().readConfiguration(resourceAsStream);
            } finally {
                resourceAsStream.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws ParseException, IOException, NotFoundException {
        initLogging();
        LOG.info("CLI: {}", Arrays.toString(args));
        final Options options = new Options()
                .addOption("l", "uri", true, "Base-URI in the form of " + DEFAULT_ARTIFACTORY_URI)
                .addOption("u", "user", true, "Username")
                .addOption("p", "password", true, "Password")
                .addOption("d", "debug", false, "Turn on debugging");
        final CommandLine commandline = new BasicParser().parse(options, args);
        if (commandline.hasOption("d")) {
            LOG.info("Setting debug");
            java.util.logging.Logger.getLogger("net.oneandone.shared.artifactory").setLevel(Level.ALL);
        }
        final List<String> argList = commandline.getArgList();
        LOG.info("ARGS: {}", argList);
        Injector injector = Guice.createInjector(new ArtifactoryModule());
        App instance = injector.getInstance(App.class);
        instance.preemptiveRequestInterceptor.addCredentialsForHost("web.de", "foo", "bar");
        List<ArtifactoryStorage> search = instance.searchByGav.search("repo1-cache", Gav.valueOf(argList.get(0)));
        LOG.info("Got {} search results", search.size());
    }

    private final SearchByGav searchByGav;

    @Inject
    public App(
            @Named("preemptiveRequestInterceptor") PreemptiveRequestInterceptor preemptiveRequestInterceptor,
            @Named("searchByGav") SearchByGav searchByGav) {
        this.preemptiveRequestInterceptor = preemptiveRequestInterceptor;
        this.searchByGav = searchByGav;
        
    }
    
    
}
