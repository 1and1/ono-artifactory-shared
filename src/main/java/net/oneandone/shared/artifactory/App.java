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
import java.net.URI;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App {

    private final static URI DEFAULT_ARTIFACTORY_URI = URI.create("http://localhost:8081/artifactory/");

    public static void main(String[] args) throws ParseException {
        final Options options = new Options()
                .addOption("l", "uri", true, "Base-URI in the form of " + DEFAULT_ARTIFACTORY_URI)
                .addOption("u", "user", true, "Username")
                .addOption("p", "password", true, "Password");
        final CommandLine commandline;
        try {
            commandline = new BasicParser().parse(options, args);
            System.out.println(commandline.getArgList());
        } catch (Exception e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.setWidth(90);
            e.printStackTrace();
            formatter.printHelp("artifactory-download: ", options);
        }

    }

    public App(URI artifactoryUri) {
        Injector injector = Guice.createInjector(new ArtifactoryModule(artifactoryUri.toString()));
        injector.getInstance(SearchByChecksum.class);
    }

    public App() {
        this(DEFAULT_ARTIFACTORY_URI);
    }
}
