package fr.rouen.univ.xquery;

import fr.rouen.univ.models.FileContent;
import org.junit.Before;
import org.junit.Test;

import javax.xml.xquery.XQException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


    @Test
    public void testGetTitles() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";
        Map<String, String> map;

        Map<String, String> mapExpected = new HashMap<>();
        mapExpected.put("29061857", "A case of blood sweating: hematohidrosis syndrome.");
        mapExpected.put("29054232", "Surgical Repair of Bland-White-Garland Syndrome With Giant Right Coronary Artery Aneurysm.");
        mapExpected.put("29054237", "Systemic Venous Rerouting Through the Coronary Sinus for ccTGA With Bilateral SVCs.");

        // When - Execute getTitles
        map = this.queryExecutor.getTitles(path, filename, extension);

        // Then - Compare with Map expected.
        for (String key : map.keySet()) {
            assertThat(mapExpected.containsKey(key)).isTrue();
            assertThat(mapExpected.get(key)).isEqualTo(map.get(key));
        }
    }

    @Test
    public void testGetAbstract() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";
        Map<String, String> map;

        Map<String, String> mapExpected = new HashMap<>();
        mapExpected.put("29054232", "A 61-year-old man was diagnosed with adult-type anomalous left coronary artery from pulmonary artery (or Bland-White-Garland syndrome) and a giant right coronary artery aneurysm. He underwent a thorough anatomic correction to excise the aneurysm and reconstruct a coronary system of two vessels. The postoperative period of this patient was uneventful.");

        // When - Execute getTitles
        map = this.queryExecutor.getAbstracts(path, filename, extension);
        System.out.println("Map : " + map);

        // Then - Compare with Map expected.
        for (String key : map.keySet()) {
            assertThat(mapExpected.containsKey(key)).isTrue();
            assertThat(mapExpected.get(key)).isEqualTo(map.get(key));
        }
    }

    @Test
    public void testMergeMaps() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";
        Map<String, String> titles = this.queryExecutor.getTitles(path, filename, extension);
        Map<String, String> abstracts = this.queryExecutor.getAbstracts(path, filename, extension);

        // When -
        Map<String, FileContent> mergeMap = this.queryExecutor.mergeMap(titles, abstracts);

        // Then -
        System.out.println(mergeMap);
    }

    @Test
    public void testCreateFiles() {
        // Given -
        final String path = "src/resources/";
        final String filename = "rare_diseases_short_ver";
        final String extension = ".xml";
        final String destinationPath = "src/resources/files/";
        final String destinationExtension = ".txt";
        Map<String, String> titles = this.queryExecutor.getTitles(path, filename, extension);
        Map<String, String> abstracts = this.queryExecutor.getAbstracts(path, filename, extension);
        Map<String, FileContent> mergeMap = this.queryExecutor.mergeMap(titles, abstracts);

        // When -
        try {
            this.queryExecutor.createFiles(mergeMap, destinationPath, destinationExtension);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
