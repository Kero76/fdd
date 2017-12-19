package fr.rouen.univ;

import fr.rouen.univ.xquery.QueryExecutor;
import fr.rouen.univ.models.DataFiller;
import fr.rouen.univ.models.PubmedArticle;
import fr.rouen.univ.parser.XmlParser;

import javax.xml.xquery.XQException;

import java.io.IOException;

import java.util.*;

public class App {
    // CLASS CONSTANTS
    public static final String[] ARGS_NAME = {"path", "folder", "short_filename", "extension", "method"};
    
    public static void main(String[] args) {
        if (args == null || args.length < ARGS_NAME.length) {
          throw new IllegalArgumentException(usage());
        }
        String path = args[0];
        String shortFilename = args[1];
        String extension = args[2];
        String method = args[3];
        
        QueryExecutor queryExecutor = new QueryExecutor();
        DataFiller dataFiller = new DataFiller();
        
        Optional<String> s = queryExecutor.getArticlesFromFile(path, shortFilename, extension);
        List<String> l = XmlParser.parseResultQueryAsList(s);
        dataFiller.fillArticlesList(l);
        List<PubmedArticle> articles = dataFiller.getArticles();
    }
    
    private static String usage() {
      StringBuilder usage = new StringBuilder("Usage : App ");
      Arrays.asList(ARGS_NAME).stream()
        .forEach(s -> {
          usage.append("<" + s + ">");
        });
      return usage.toString();
    }
}
