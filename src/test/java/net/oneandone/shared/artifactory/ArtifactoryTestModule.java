/*
 * Copyright 2013 1&1.
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.internal.AssumptionViolatedException;

/**
 * @author Mirko Friedenhagen
 */
public class ArtifactoryTestModule extends ArtifactoryModule {

    public ArtifactoryTestModule() {
        super();
        try {
            final URL url = new URL(getArtifactoryUrl());
            try {
                url.openStream().close();
            } catch (IOException ex) {
                throw new AssumptionViolatedException("Could not reach " + url);
            }
        } catch (MalformedURLException ex) {
            throw new AssumptionViolatedException("Malformed URL", ex);
        }
    }    
}
