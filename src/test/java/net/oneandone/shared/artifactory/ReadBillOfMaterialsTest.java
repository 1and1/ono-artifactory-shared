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

import net.oneandone.shared.artifactory.model.BomEntry;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Mirko Friedenhagen
 */
public class ReadBillOfMaterialsTest {
    final InputStream bomStream = ReadBillOfMaterialsTest.class.getResourceAsStream("/bill-of-materials.txt");
    
    @After
    public void tearDown() throws IOException {
        bomStream.close();
    }
    
    @Test
    public void read() throws IOException {
        List<BomEntry> bomEntries = BomEntry.read(bomStream);
        assertThat(bomEntries).hasSize(5);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
