/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import net.oneandone.shared.artifactory.DateTimeDeserializer;
import net.oneandone.shared.artifactory.MD5Deserializer;
import net.oneandone.shared.artifactory.Sha1Deserializer;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mirko
 */
public class ArtifactoryResultsTest {
    
    @Test
    public void checkDeserializationOfStorageData() throws IOException {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(Sha1.class, new Sha1Deserializer())
                .registerTypeAdapter(MD5.class, new MD5Deserializer())
                .create();
        final InputStreamReader reader = new InputStreamReader(
                ArtifactoryResultsTest.class.getResourceAsStream("/junit-4.11-storage.json"));
        List<ArtifactoryStorage> results;
        try {
            results = gson.fromJson(reader, ArtifactoryResults.class).results;
        } finally {
            reader.close();
        }
        assertEquals(4, results.size());
        final ArtifactoryStorage pomInfo = results.get(0);
        assertEquals("anonymous", pomInfo.createdBy);
        assertEquals(Sha1.valueOf("cddf7490ffe839978cf5d6c944c01f2a8cb70a49"), pomInfo.checksums.sha1);
    }
    
}
