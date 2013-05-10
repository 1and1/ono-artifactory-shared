/*
 * Copyright 2013 1&1.
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
            if (shortName.equals(jenkinsPlugin.getShortName()) && url.equals(jenkinsPlugin.getUrl())) {
                return jenkinsPlugin;
            }
        }
        throw new IllegalArgumentException("No Jenkins Plugin matches " + shortName + ", " + url);
    }
    
}
