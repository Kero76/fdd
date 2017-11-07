package fr.rouen.univ.xquery;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.junit.Before;
import org.junit.Test;

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
    public void testQueryMethod() {
        // Given -
        String request = "";

        try {
            XdmValue result = queryExecutor.query(request);
            System.out.println(result.toString());
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
    }
}
