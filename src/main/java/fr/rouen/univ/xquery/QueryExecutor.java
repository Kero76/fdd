package fr.rouen.univ.xquery;

import com.saxonica.xqj.SaxonXQDataSource;
import fr.rouen.univ.models.FileContent;
import fr.rouen.univ.models.FileContentMesh;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryExecutor {

    private static Logger logger = Logger.getLogger("QueryExecutor");

    private static String REGEX_SEPARATOR = "--";

    private XQConnection connection;

    public QueryExecutor() {
        SaxonXQDataSource ds = new SaxonXQDataSource();
        try {
            this.connection = ds.getConnection();
        } catch (XQException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create files.
     *
     * @param map
     *  Map who contain information at insert on file.
     * @param path
     *  Path to locate generated files.
     * @param extension
     *  Extension of the generated files.
     */
    public void createFiles(Map<String, FileContent> map, String path, String extension) throws IOException {
        Writer writer = null;
        for (String key : map.keySet()) {
            try {
                writer = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(
                            path + map.get(key).getPmid() + extension
                        )
                    )
                );
                logger.info("Writer write content on file");
                writer.write("T. "
                        + map.get(key).getTitle()
                        + System.getProperty("line.separator")
                        + "A. "
                        + map.get(key).getContent()
                );
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    /**
     * Create indexed files.
     *
     * @param map
     *  Map who contain information at insert on file.
     * @param path
     *  Path to locate generated files.
     * @param extension
     *  Extension of the generated files.
     * @throws IOException
     */
    public void createIndexesFiles(Map<String, FileContentMesh> map, String path, String extension) throws IOException {
        Writer writer = null;
        for (String key : map.keySet()) {
            try {
                writer = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(
                    path + map.get(key).getPmid() + "_indexed" + extension
                        )
                    )
                );
                logger.info("Writer write content on indexed file");
                writer.write("T. "
                             + map.get(key).getTitle()
                             + System.getProperty("line.separator")
                             + "A. "
                             + map.get(key).getContent()
                             + System.getProperty("line.separator")
                             + "I. "
                             + map.get(key).getMeshes()
                );
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    /**
     * Merge Two map.
     *
     * @param titles
     *  Map who contains all titles.
     * @param abstracts
     *  Map who contains all abstracts.
     * @return
     *  A map who contain the merge of two maps.
     */
    public Map<String, FileContent> mergeMap(Map<String, String> titles, Map<String, String> abstracts) {
        Map<String, FileContent> map = new HashMap<>();
        for (String s : abstracts.keySet()) {
            map.put(s, new FileContent(s, titles.get(s), abstracts.get(s)));
        }

        return map;
    }

    /**
     * Merge three indexed map.
     *
     * @param titles
     *  Map who contains all titles.
     * @param abstracts
     *  Map who contains all abstracts.
     * @param meshes
     *  Map who contains all meshes.
     * @return
     *  A map who contain the merge of two maps.
     */
    public Map<String, FileContentMesh> mergeIndexedMap(Map<String, String> titles, Map<String, String> abstracts, Map<String, String> meshes) {
        Map<String, FileContentMesh> map = new HashMap<>();
        for (String s : abstracts.keySet()) {
            map.put(s, new FileContentMesh(s, titles.get(s), abstracts.get(s), meshes.get(s)));
        }

        return map;
    }

    /**
     * Get all Titles present on file.
     *
     * @param path Path of the file at load.
     * @param filename Filename who contain information at split.
     * @param extension File extension.
     *
     * @return A map who contains the pmid and title.
     */
    public Map<String, String> getTitles(String path, String filename, String extension) {
        // Build request.
        String fieldsSelected = "PubmedArticleSet/PubmedArticle";
        String loop = "for $article in $fdd";
        String returnFields = "($article//MedlineCitation/PMID/text(), ',', $article//ArticleTitle/text(), '" + REGEX_SEPARATOR + "')\n";
        String query = this.buildQuery(path, filename, extension, fieldsSelected, loop, returnFields);

        // Map with all titles.
        Map<String, String> titles = new HashMap<>();
        try {
            // Execute query.
            XQPreparedExpression expr = this.connection.prepareExpression(query);
            XQSequence result = expr.executeQuery();
            List<String> l = (this.parseString(result));

            // Create Map to split PMID as key and title as value.
            for (String s : l) {
                titles.put(s.split(",")[0], s.split(",")[1]);
            }
        } catch (XQException e) {
            e.printStackTrace();
        }

        return titles;
    }

    /**
     * Get all Abstract present on file.
     *
     * @param path Path of the file at load.
     * @param filename Filename who contain information at split.
     * @param extension File extension.
     *
     * @return A map who contains the pmid and abstract.
     */
    public Map<String, String> getAbstracts(String path, String filename, String extension) {
        // Build request.
        String fieldsSelected = "PubmedArticleSet/PubmedArticle";
        String loop = "for $article in $fdd";
        String returnFields = "if (fn:empty($article//Abstract/AbstractText/text())) then (\n"
                + "($article//MedlineCitation/PMID/text(), ',', 'No Data.', '" + REGEX_SEPARATOR + "')\n"
                + ") else (\n"
                + "($article//MedlineCitation/PMID/text(), ',', $article//Abstract/AbstractText/text(), '" + REGEX_SEPARATOR + "')\n"
                + ")";
        String query = this.buildQuery(path, filename, extension, fieldsSelected, loop, returnFields);

        // Map with all abstracts.
        Map<String, String> abstracts = new HashMap<>();
        try {
            // Execute query.
            XQPreparedExpression expr = this.connection.prepareExpression(query);
            XQSequence result = expr.executeQuery();
            List<String> l = (this.parseString(result));

            // Create Map to split PMID as key and abstract as value.
            for (String s : l) {
                if (s.split(",").length == 2 && !s.split(",")[1].contains(" No Data.")) {
                    abstracts.put(s.split(",")[0], s.split(",")[1]);
                }
            }
        } catch (XQException e) {
            e.printStackTrace();
        }

        return abstracts;
    }

    /**
     * Get all Meshes present on file.
     *
     * @param path Path of the file at load.
     * @param filename Filename who contain information at split.
     * @param extension File extension.
     *
     * @return A map who contains the pmid and meshs.
     */
    public Map<String, String> getMeshes(String path, String filename, String extension) {
        // Build request.
        String fieldsSelected = "PubmedArticleSet/PubmedArticle";
        String loop = "for $article in $fdd";
        String returnFields = "($article//MedlineCitation/PMID/text(), '|', $article//MeshHeadingList/MeshHeading/DescriptorName/text(), '" + REGEX_SEPARATOR + "')\n";
        String query = this.buildQuery(path, filename, extension, fieldsSelected, loop, returnFields);

        // Map with all abstracts.
        Map<String, String> meshes = new HashMap<>();
        try {
            // Execute query.
            XQPreparedExpression expr = this.connection.prepareExpression(query);
            XQSequence result = expr.executeQuery();
            List<String> l = (this.parseString(result));

            // Create Map to split PMID as key and abstract as value.
            for (String s : l) {
                if (!s.split("[|]")[1].contains(" No Data.")) {
                    String meshParse = this.meshParse(s.split("[|]")[1]);
                    meshes.put(s.split("[|]")[0], meshParse);
                }
            }
        } catch (XQException e) {
            e.printStackTrace();
        }

        return meshes;
    }

    /**
     * Build query.
     *
     * @param path Path of file at request.
     * @param filename Filename at request.
     * @param extension Extension of the file.
     * @param fieldsSelected Selected fields on request.
     * @param loop "For" string on request.
     * @param returnFields Return fields of the request.
     *
     * @return The query entirely build.
     */
    private String buildQuery(String path, String filename, String extension, String fieldsSelected, String loop, String returnFields) {
        // Build query.
        StringBuilder query = new StringBuilder();
        query.append("let $fdd := doc(\"");
        query.append(path);
        query.append(filename);
        query.append(extension);
        query.append("\")/");
        query.append(fieldsSelected + "\n");
        query.append(loop + "\n");
        query.append("return " + returnFields);

        return query.toString();
    }

    /**
     * Transform a XQSequence as List of String.
     *
     * @param sequence Sequence at convert on List of string.
     *
     * @return A list of all strings.
     *
     * @throws XQException
     */
    private List<String> parseString(XQSequence sequence)
            throws XQException {
        List<String> l = new ArrayList<>();
        String queryResult = sequence.getSequenceAsString(null);
        l.addAll(Arrays.asList(queryResult.split(REGEX_SEPARATOR)));

        return l;
    }

    /**
     * Parse mesh string.
     *
     * @param meshes
     *  Mesh at parse.
     * @return
     *  A string who contain mesh separate with '|' separator.
     */
    private String meshParse(String meshes) {
        String separator = "|";
        meshes = meshes.replace(" ", "");
        StringBuilder out = new StringBuilder(meshes.replace(",", ""));

        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(meshes);
        int extraFeed = -1;
        while (m.find()) {
            if (m.start() != 0) {
                out = out.insert(m.start() + extraFeed, separator);
                extraFeed++;
            }
        }

        return out.toString();
    }
}
