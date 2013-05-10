/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory.model;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Mirko Friedenhagen
 */
public class JenkinsPluginsTest {

    Gson gson = new GsonBuilder().create();
    InputStreamReader in = new InputStreamReader(
            JenkinsPluginsTest.class.getResourceAsStream("/jenkins-plugins.json"), Charsets.UTF_8);
    JenkinsPlugins jenkinsPlugins = gson.fromJson(in, JenkinsPlugins.class);

    @After
    public void closeResource() throws IOException {
        in.close();
    }

    @Test
    public void testGetExistingJenkinsPlugin() {
        assertEquals("1.4", jenkinsPlugins.getByShortNameAndUrl("mailer", "http://wiki.jenkins-ci.org/display/JENKINS/Mailer").getVersion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExistingJenkinsPluginWhichDoesNotExist() {
        jenkinsPlugins.getByShortNameAndUrl("mailerXXX", "http://wiki.jenkins-ci.org/display/JENKINS/Mailer").getVersion();
    }
}
