package fr.rouen.univ.xquery;

import com.saxonica.xqj.SaxonXQDataSource;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class QueryExecutor {

    private static Logger logger = Logger.getLogger("QueryExecutor");

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
     * Get all Meshes present on file.
     *
     * @param path Path of the file at load.
     * @param filename Filename who contain information at split.
     * @param extension File extension.
     *
     * @return A map who contains the pmid and meshs.
     */
    public Optional<String> getArticlesFromFile(String path, String filename, String extension) {
        // Build request.
        String fieldsSelected = "PubmedArticleSet/PubmedArticle";
        String loop = "for $article in $fdd";
        String returnFields =
                "let $pmid := $article//MedlineCitation/PMID"
                        + System.getProperty("line.separator")
                        + "let $pmid := $article//MedlineCitation/PMID"
                        + System.getProperty("line.separator")
                        + "let $titles := $article//ArticleTitle"
                        + System.getProperty("line.separator")
                        + "let $abstracts := $article//Abstract/AbstractText"
                        + System.getProperty("line.separator")
                        + "let $meshes := $article//MeshHeadingList/MeshHeading"
                    + System.getProperty("line.separator")
                    + "return <article>{$pmid}{$titles}{$abstracts}{$meshes}</article>";
        String query = this.buildQuery(path, filename, extension, fieldsSelected, loop, returnFields);

        // Map with all abstracts.
        Map<String, String> meshes = new HashMap<>();
        try {
            logger.info("Prepared query");
            // Execute query.
            XQPreparedExpression expr = this.connection.prepareExpression(query);
            XQSequence result = expr.executeQuery();
            logger.info("Execute query");
            return Optional.of(result.getSequenceAsString(null));
        } catch (XQException e) {
            e.printStackTrace();
        }
        return Optional.empty();
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
        logger.info("Start building query");

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

        logger.info("Building complete : " + query.toString());

        return query.toString();
    }
}
