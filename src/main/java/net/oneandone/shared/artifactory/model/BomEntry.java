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

import com.google.common.base.Charsets;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.io.LineReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mirko Friedenhagen
 */
public class BomEntry {
    private static final Pattern PATTERN = Pattern.compile("(" + Sha1.PATTERN.toString() + ")  (" + ".*)");
    private final String fileName;
    private final Sha1 sha1;

    private BomEntry(final Sha1 sha1, final String fileName) {
        this.fileName = fileName;
        this.sha1 = sha1;
    }

    public static BomEntry valueOf(final String line) {
        checkNotNull(line, "line must not be null!");
        final Matcher matcher = PATTERN.matcher(line);
        checkArgument(matcher.matches(), line + " does not match " + PATTERN.toString());
        return new BomEntry(Sha1.valueOf(matcher.group(1)), matcher.group(2));
    }

    /**
     * Reads all sha1 sum entries from the given input stream.
     * 
     * @param in to read from
     * @return list of BomEntry
     * @throws IOException
     */
    public static List<BomEntry> read(final InputStream in) throws IOException {
        final LineReader lineReader = new LineReader(new InputStreamReader(checkNotNull(in), Charsets.UTF_8));
        final ArrayList<BomEntry> bomEntries = new ArrayList<BomEntry>();
        String readLine = lineReader.readLine();
        while (readLine != null) {
            if (!readLine.startsWith("#")) {
                bomEntries.add(BomEntry.valueOf(readLine));
            }
            readLine = lineReader.readLine();
        }
        return bomEntries;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the sha1
     */
    public Sha1 getSha1() {
        return sha1;
    }
    
}
