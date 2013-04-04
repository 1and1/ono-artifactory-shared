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
 * <a href="http://en.wikipedia.org/wiki/SHA-1">SHA-1</a> Holder class.
 * 
 * @author Mirko Friedenhagen
 */
public class Sha1 {

    private final static int LEN_OF_SHA1 = 40;
    final static Pattern PATTERN = Pattern.compile("[a-fA-F0-9]{40}");
    private final String sha1;

    private Sha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * Returns an SHA1-instance from the hexadecimal representation.
     * 
     * @param sha1
     * @return
     */
    public static Sha1 valueOf(final String sha1) {
        checkNotNull(sha1, "Given sha1 must not be null.");
        final int length = sha1.length();
        checkArgument(length == LEN_OF_SHA1, "Length of SHA1 must be " + LEN_OF_SHA1 + ", got " + length);
        checkArgument(PATTERN.matcher(sha1).matches(), sha1 + " does not match " + PATTERN.toString());
        return new Sha1(sha1);
    }
    
    /**
     * Returns the hexadecimal representation of the SHA1-instance.
     */
    @Override
    public String toString() {
        return sha1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sha1 other = (Sha1) obj;
        return this.sha1.equals(other.sha1);
    }

    @Override
    public int hashCode() {
        return sha1.hashCode();
    }

}
