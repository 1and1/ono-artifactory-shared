/*
 * Copyright 2013 1&1.
 */

package net.oneandone.shared.artifactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import net.oneandone.shared.artifactory.model.ArtifactoryStorage;
import net.oneandone.shared.artifactory.model.Gav;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mirko Friedenhagen.
 */
public class SearchByGavIT {

    final SearchByGav sut;
    final String repositoryName = "repo1-cache";
    final Gav gav = new Gav("junit", "junit", "4.11");

    public SearchByGavIT() {
        Injector injector = Guice.createInjector(new ArtifactoryModule());
        sut = injector.getInstance(SearchByGav.class);
    }

    /**
     * Test of search method, of class SearchByGav.
     */
    @Test
    public void testSearch() throws Exception {
        List<ArtifactoryStorage> result = sut.search(repositoryName, gav);
        assertEquals(4, result.size());
    }
}
