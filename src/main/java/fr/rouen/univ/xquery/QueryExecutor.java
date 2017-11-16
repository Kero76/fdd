package fr.rouen.univ.xquery;

import com.saxonica.xqj.SaxonXQDataSource;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;
import java.io.*;
import java.util.logging.Logger;

public class QueryExecutor {

    private static Logger logger = Logger.getLogger("QueryExecutor");

    /**
     * Parse a .xml file and write on a file the result of the request.
     * @param path
     *  Path to .xml file at parse.
     * @param filename
     *  Name of the xml file at parse.
     * @param extension
     *  Extension of the file at parse.
     * @throws XQException
     * @throws IOException
     */
    public void queryInFile(String path, String filename, String extension)
            throws XQException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        stringBuilder.append(filename);
        stringBuilder.append(extension);

        logger.info("Prepare and execute query");
        SaxonXQDataSource ds = new SaxonXQDataSource();
        XQConnection con = ds.getConnection();
        String query = "let $fdd := doc(\"" + stringBuilder.toString() + "\")/PubmedArticleSet/PubmedArticle\n" +
                "for $article in $fdd\n" +
                "return ('\nT. ',$article//ArticleTitle/text())";
        XQPreparedExpression expr = con.prepareExpression(query);
        XQSequence result = expr.executeQuery();

        logger.info("Try to write result of query on file");
        Writer writer = null;
        try {
            writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(
                        "src/resources/files/" + filename + ".txt"
                    )
                )
            );
            logger.info("Writer write content on file");
            writer.write(result.getSequenceAsString(null));
        } catch (Exception e) {
            logger.info("Error while writing in file" + filename + " : " + e.getMessage());
        } finally {
            logger.info("The object Writer is close.");
            writer.close();
        }

        result.close();
        expr.close();
        con.close();
    }
}
