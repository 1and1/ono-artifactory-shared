/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory;

import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Mirko Friedenhagen
 */
public class UtilsTest {

    /**
     * Test of toUri method, of class Utils.
     */
    @Test
    public void testToUri() {
        URI expResult = URI.create("http://localhost/");
        URIBuilder uriBuilder = new URIBuilder(expResult);
        String errorMessage = "oops";
        URI result = Utils.toUri(uriBuilder, errorMessage);
        assertEquals(expResult, result);
    }

    /**
     * Test of toUri method, of class Utils.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToUriFailing() throws URISyntaxException {
        URIBuilder mockUriBuilder = mock(URIBuilder.class);
        when(mockUriBuilder.build()).thenThrow(new URISyntaxException("Oops", "Oops"));
        Utils.toUri(mockUriBuilder, "does not matter");
    }

    /**
     * Test of createGson method, of class Utils.
     */
    @Test
    public void testCreateGson() {
        Gson result = Utils.createGson();
        assertNotNull(result);
    }
}
