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
package net.oneandone.shared.artifactory.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Mirko Friedenhagen
 */
public class JenkinsPlugins {

    private List<JenkinsPlugin> plugins;

    public static class JenkinsPlugin {
        private String shortName;
        private String url;
        private String version;

        /**
         * @return the shortName
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @return the version
         */
        public String getVersion() {
            return version;
        }
    }

    public JenkinsPlugin getByShortNameAndUrl(String shortName, String url) {
        for (JenkinsPlugin jenkinsPlugin : plugins) {
            final String pluginShortName = jenkinsPlugin.getShortName();
            final String pluginUrl = jenkinsPlugin.getUrl();
            if (shortName.equals(pluginShortName) && url.equals(pluginUrl)) {
                return jenkinsPlugin;
            }
        }
        throw new IllegalArgumentException("No Jenkins Plugin matches " + shortName + ", " + url);
    }

}
