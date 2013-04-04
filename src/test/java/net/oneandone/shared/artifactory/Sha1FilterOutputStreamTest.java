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

import net.oneandone.shared.artifactory.Sha1FilterOutputStream;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import net.oneandone.shared.artifactory.model.Sha1;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mirko Friedenhagen <mirko.friedenhagen@1und1.de>
 */
public class Sha1FilterOutputStreamTest {

    /**
     * Test of write method, of class Sha1FilterOutputStream.
     */
    @Test
    public void test() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Sha1FilterOutputStream sut = new Sha1FilterOutputStream(out);
        final String inputString = "hallo";
        try {            
            ByteStreams.copy(new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8)), sut);
            sut.write('A');
        }
        finally {
            sut.close();
        }
        final String outputString = out.toString("utf-8");
        final Sha1 expectedSha1 = Sha1.valueOf("1f8782aaed6492b40ac2fc2584bca557d9fd3c11");
        assertEquals(expectedSha1, sut.getSha1());
        assertEquals(inputString + "A", outputString);
    }
}
