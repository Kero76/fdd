package fr.rouen.univ.xquery;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryExecutorTest {

    /**
     * Objet QueryExecutor.
     */
    private QueryExecutor queryExecutor;

    @Before
    public void setUp() {
        this.queryExecutor = new QueryExecutor();
    }

    @Test
    public void testGetArticlesFromFile() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";

        // When -
        Optional<String> s = this.queryExecutor.getArticlesFromFile(path, filename, extension);

        // Then -
        assertThat(s.get()).isNotNull();
        System.out.println(s.get());
    }
}
