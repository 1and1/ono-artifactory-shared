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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.regex.Pattern;

/**
 * <a href="http://en.wikipedia.org/wiki/Md5">MD5</a> Holder class.
 * 
 * @author Mirko Friedenhagen
 */
public class MD5 {

    private final static int LEN_OF_MD5 = 32;
    final static Pattern PATTERN = Pattern.compile("[a-fA-F0-9]{32}");
    private final String md5;

    private MD5(String md5) {
        this.md5 = md5;
    }

    /**
     * Returns an MD5-instance from the hexadecimal representation.
     * 
     * @param md5
     * @return
     */
    public static MD5 valueOf(final String md5) {
        checkNotNull(md5, "Given md5 must not be null.");
        final int length = md5.length();
        checkArgument(length == LEN_OF_MD5, "Length of MD5 must be " + LEN_OF_MD5 + ", got " + length);
        checkArgument(PATTERN.matcher(md5).matches(), md5 + " does not match " + PATTERN.toString());
        return new MD5(md5);
    }
    
    /**
     * Returns the hexadecimal representation of the MD5-instance.
     */
    @Override
    public String toString() {
        return md5;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MD5 other = (MD5) obj;
        return this.md5.equals(other.md5);
    }

    @Override
    public int hashCode() {
        return md5.hashCode();
    }

}
