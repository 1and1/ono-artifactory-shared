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
import static com.google.common.base.Preconditions.checkState;

/**
 *
 * @author Mirko Friedenhagen
 */
public class JenkinsPlugins {

    /** Holder for the plugins. */
    private List<JenkinsPlugin> plugins;

    /**
     * Model for a single plugin.
     */
    public static class JenkinsPlugin {
        /**
         * Shortname of the plugin.
         */
        private String shortName;
        /**
         * URL of the plugin.
         */
        private String url;
        /**
         * Version of the plugin.
         */
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

    /**
     * Get the instance of a plugin identified by shortName and url.
     *
     * @param shortName of the plugin.
     * @param url of the plugin.
     * @return plugin instance.
     */
    public JenkinsPlugin getByShortNameAndUrl(String shortName, String url) {
        checkState(plugins != null, "JenkinsPlugins seems not be properly initialized, did you deserialize a JSON file?");
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
