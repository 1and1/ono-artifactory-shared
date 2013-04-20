/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory.model;

import java.util.Locale;

/**
 *
 * @author mirko
 */
public class Gav {
    private final String groupId;
    private final String artifactId;
    private final String version;
    
    Gav(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;        
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "GAV(%s, %s, %s)", groupId, artifactId, version);
    }
    
    
}
