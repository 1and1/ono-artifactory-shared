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

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import net.oneandone.shared.artifactory.model.MD5;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Calculates the {@link MD5} of the decorated {@link OutputStream}.
 *
 * @author Mirko Friedenhagen &lt;mirko.friedenhagen@1und1.de&gt;
 */
class MD5FilterOutputStream extends FilterOutputStream {
    private final Hasher hasher = Hashing.md5().newHasher();

    /**
     * @param out to decorate.
     */
    public MD5FilterOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        hasher.putBytes(b, off, len);
        out.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        hasher.putInt(b);
        out.write(b);
    }

    public MD5 getMD5() {
        return MD5.valueOf(hasher.hash().toString());
    }

}
