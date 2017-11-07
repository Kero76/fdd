package fr.rouen.univ.xquery;

import net.sf.saxon.s9api.*;

public class QueryExecutor {

    public XdmValue query(String request)
            throws SaxonApiException {
        Processor processor = new Processor(false);
        XQueryCompiler qc = processor.newXQueryCompiler();
        XQueryExecutable exp1 = qc.compile(
                "declare function local:t1($v1 as xs:integer, $v2 as xs:double*) { " +
                        "   $v1 div sum($v2)" +
                        "};" +
                        "10");
        final XQueryEvaluator ev = exp1.load();
        XdmValue v1 = new XdmAtomicValue(14);
        XdmValue v2 = new XdmAtomicValue(5).append(new XdmAtomicValue(2));
        XdmValue result = ev.callFunction(new QName("http://www.w3.org/2005/xquery-local-functions", "t1"), new XdmValue[]{v1, v2});

        return result;
    }
}
