package fr.rouen.univ.files;

import fr.rouen.univ.parser.XmlParser;
import fr.rouen.univ.xquery.QueryExecutor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FileCreatorTest {

    private QueryExecutor queryExecutor;
    private FileCreator fileCreator;

    @Before
    public void setUp() {
        this.queryExecutor = new QueryExecutor();
        this.fileCreator = new FileCreator();
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
        this.fileCreator.fillArticlesList(l);

        // Then -
        assertThat(this.fileCreator.getArticles().size()).isEqualTo(sizeExpected);
        System.out.println(this.fileCreator.getArticles());

    }
}
