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

import net.oneandone.shared.artifactory.MD5FilterOutputStream;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import net.oneandone.shared.artifactory.model.MD5;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mirko Friedenhagen <mirko.friedenhagen@1und1.de>
 */
public class MD5FilterOutputStreamTest {

    /**
     * Test of write method, of class MD5FilterOutputStream.
     */
    @Test
    public void test() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final MD5FilterOutputStream sut = new MD5FilterOutputStream(out);
        final String inputString = "hallo";
        try {            
            ByteStreams.copy(new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8)), sut);
            sut.write('A');
        }
        finally {
            sut.close();
        }
        final String outputString = out.toString("utf-8");
        final MD5 expectedMD5 = MD5.valueOf("833fb495d0afcba2f4d22bef1b7ffbea");
        assertEquals(expectedMD5, sut.getMD5());
        assertEquals(inputString + "A", outputString);
    }
}
