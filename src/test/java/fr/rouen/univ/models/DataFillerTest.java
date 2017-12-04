package fr.rouen.univ.models;

import fr.rouen.univ.parser.XmlParser;
import fr.rouen.univ.xquery.QueryExecutor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DataFillerTest {

    private QueryExecutor queryExecutor;
    private DataFiller dataFiller;

    @Before
    public void setUp() {
        this.queryExecutor = new QueryExecutor();
        this.dataFiller = new DataFiller();
    }

    @Test
    public void testFillArticlesList() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";
        Optional<String> s = this.queryExecutor.getArticlesFromFile(path, filename, extension);
        List<String> l = XmlParser.parseResultQueryAsList(s);
        int sizeExpected = 2;

        // When -
        this.dataFiller.fillArticlesList(l);

        // Then -
        assertThat(this.dataFiller.getArticles().size()).isEqualTo(sizeExpected);
        System.out.println(this.dataFiller.getArticles());
    }
}
