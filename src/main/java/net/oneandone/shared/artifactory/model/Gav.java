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

import java.util.Locale;

/**
 *
 * @author mirko
 */
public class Gav {
    private final String groupId;
    private final String artifactId;
    private final String version;
    
    public Gav(String groupId, String artifactId, String version) {
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
    
    public static Gav valueOf(String gav) {
        String[] split = gav.split(":");
        if (split.length != 3) {
            throw new IllegalArgumentException("Expected GROUP_ID:ARTIFACT_ID:VERSION, got " + gav);
        } else {
            return new Gav(split[0], split[1], split[2]);
        }
    }
    
}
