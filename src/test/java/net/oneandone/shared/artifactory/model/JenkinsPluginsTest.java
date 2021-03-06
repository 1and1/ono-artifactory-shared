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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testGetByShortNameAndUrl() {
        assertThat(
            jenkinsPlugins.getByShortNameAndUrl("mailer", "http://wiki.jenkins-ci.org/display/JENKINS/Mailer").getVersion()).isEqualTo("1.4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByShortNameAndUrlWhoseShortNameDoesNotMatch() {
        jenkinsPlugins.getByShortNameAndUrl("mailerXXX", "http://wiki.jenkins-ci.org/display/JENKINS/Mailer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByShortNameAndUrlWhoseUrlDoesNotMatch() {
        jenkinsPlugins.getByShortNameAndUrl("mailer", "http://wiki.jenkins-ci.org/display/JENKINS/MailerXXX");
    }
    @Test(expected = IllegalStateException.class)
    public void testGetByShortNameAndUrlWithUninitializedClassNotComingFromDeserialization() {
        new JenkinsPlugins().getByShortNameAndUrl("mailer", "http://wiki.jenkins-ci.org/display/JENKINS/MailerXXX");
    }
}
