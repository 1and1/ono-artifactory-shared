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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mirko Friedenhagen
 */
public class GavTest {
    Gav sut = Gav.valueOf("commons-logging:commons-logging:1.1.1");
    
    /**
     * Test of getGroupId method, of class Gav.
     */
    @Test
    public void testGav() {
        assertThat(sut.getGroupId()).isEqualTo("commons-logging");
        assertThat(sut.getArtifactId()).isEqualTo("commons-logging");
        assertThat(sut.getVersion()).isEqualTo( "1.1.1" );
    }

    /**
     * Test of toString method, of class Gav.
     */
    @Test
    public void testToString() {
        String expResult = "GAV(commons-logging, commons-logging, 1.1.1)";
        String result = sut.toString();
        assertThat(result).isEqualTo( expResult );
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalGAV() {
        Gav.valueOf("commons-logging:commons-logging:1.1.1:DOES_NOT_MATTER");
    }
}
