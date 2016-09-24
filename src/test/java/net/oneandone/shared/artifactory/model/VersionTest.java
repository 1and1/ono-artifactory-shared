package net.oneandone.shared.artifactory.model;

import net.oneandone.shared.artifactory.Utils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionTest {

    @Test
    public void checkDeserialization() {
        final String body = "{\n" +
                "  \"version\" : \"3.4.0\",\n" +
                "  \"revision\" : \"30125\",\n" +
                "  \"addons\" : [ ],\n" +
                "  \"license\" : \"Artifactory OSS\"\n" +
                "}\n";
        final Version version = Utils.createGson().fromJson(body, Version.class);
        assertThat(version.license).isEqualTo("Artifactory OSS");
    }

}
