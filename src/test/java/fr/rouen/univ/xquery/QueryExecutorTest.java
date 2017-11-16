package fr.rouen.univ.xquery;

import org.junit.Before;
import org.junit.Test;

import javax.xml.xquery.XQException;
import java.io.IOException;

public class QueryExecutorTest {

    /**
     * Objet QueryExecutor.
     */
    private QueryExecutor queryExecutor;

    @Before
    public void setUp() {
        this.queryExecutor = new QueryExecutor();
    }

//    @Test
//    public void testQueryMethod() {
//        // Given -
//        String pathfile = "src/resources/rare_diseases_short_ver.xml";
//        String request = "let $data := doc(\"" + pathfile + "\")/PubmedArticleSet/PubmedArticle"
//                + System.getProperty("line.separator")
//                + "for $d in $data"
//                + System.getProperty("line.separator")
//                + "return ($d/MedlineCitation/Article/ArticleTitle/text())";
//
//        try {
//            String result = queryExecutor.query(pathfile, request);
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XQException e) {
//            e.printStackTrace();
//        }
//    }
}
