package fr.rouen.univ.parser;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlParserTest {

    private XmlParser parser;

    @Before
    public void setUp() {
        this.parser = parser;
    }

    @Test
    public void test() {
        // Given - String at parse.
        String atParse = "<PMID Version=\"1\">29054237</PMID>";
        String resultExpected = "29054237";

        // When - Parse String.
        String result = XmlParser.parseLine(atParse);

        // Then - Must be true.
        assertThat(result).isEqualTo(resultExpected);

    }
}
