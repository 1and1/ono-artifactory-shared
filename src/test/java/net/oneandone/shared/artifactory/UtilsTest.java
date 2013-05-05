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
