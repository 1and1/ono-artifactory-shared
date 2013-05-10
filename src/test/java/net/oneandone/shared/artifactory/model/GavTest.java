/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Mirko Friedenhagen
 */
public class GavTest {
    Gav sut = new Gav("commons-logging", "commons-logging", "1.1.1");
    
    /**
     * Test of getGroupId method, of class Gav.
     */
    @Test
    public void testGav() {
        assertEquals("commons-logging", sut.getGroupId());
        assertEquals("commons-logging", sut.getArtifactId());
        assertEquals("1.1.1", sut.getVersion());
    }

    /**
     * Test of toString method, of class Gav.
     */
    @Test
    public void testToString() {
        String expResult = "GAV(commons-logging, commons-logging, 1.1.1)";
        String result = sut.toString();
        assertEquals(expResult, result);
    }    
}
