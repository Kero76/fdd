package fr.rouen.univ.files;

import fr.rouen.univ.models.DataFiller;
import fr.rouen.univ.models.PubmedArticle;
import fr.rouen.univ.parser.XmlParser;
import fr.rouen.univ.xquery.QueryExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FileGeneratorTest {

    private List<PubmedArticle> articles;

    @Before
    public void setUp() {
        QueryExecutor queryExecutor = new QueryExecutor();
        DataFiller dataFiller = new DataFiller();

        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";

        Optional<String> s = queryExecutor.getArticlesFromFile(path, filename, extension);
        List<String> l = XmlParser.parseResultQueryAsList(s);
        dataFiller.fillArticlesList(l);
        articles = dataFiller.getArticles();
    }

    @Test
    public void iCreateUnindexFiles()
            throws IOException {
        // Arrange
        final String path = "src/resources/files/unindexed/";
        final String extension = ".txt";

        // Act
        FileGenerator.fileUnindexed(path, extension, this.articles);

        // Assert
    }

    @Test
    public void iCreateIndexFiles()
            throws IOException {
        // Arrange
        final String path = "src/resources/files/indexed/";
        final String extension = ".txt";

        // Act
        FileGenerator.fileIndexed(path, extension, this.articles);

        // Assert
    }

    @Test
    public void iCreateTitleFiles()
            throws IOException {
        // Arrange
        final String path = "src/resources/files/titles/";
        final String extension = ".txt";

        // Act
        FileGenerator.fileWithTitleContent(path, extension, this.articles);

        // Assert
    }

    @Test
    public void iCreateAbstractFiles()
            throws IOException {
        // Arrange
        final String path = "src/resources/files/abstracts/";
        final String extension = ".txt";

        // Act
        FileGenerator.fileWithAbstractContent(path, extension, this.articles);

        // Assert
    }
}
