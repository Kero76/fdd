package fr.rouen.univ.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class XmlParser {

    /**
     * Parse query result as List of String.
     *
     * @param result
     *  Result of the query at parse by \n.
     * @return
     *  A list of all lines.
     */
    public static List<String> parseResultQueryAsList(Optional<String> result) {
        return Arrays.asList(result.get().split("\n"));
    }

    /**
     * Parse line who contains tag.
     *
     * @param line
     *  Line at parse.
     * @return
     *  The content of the tag.
     */
    public static String parseLine(String line) {
        return line.split(">")[1].split("<")[0];
    }
}
