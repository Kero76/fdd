package fr.rouen.univ;

import fr.rouen.univ.xquery.QueryExecutor;
import fr.rouen.univ.models.DataFiller;
import fr.rouen.univ.models.PubmedArticle;
import fr.rouen.univ.parser.XmlParser;
import fr.rouen.univ.files.FileGenerator;

import javax.xml.xquery.XQException;

import java.io.IOException;

import java.util.*;

public class App {
    // CLASS CONSTANTS
    public static final String[] ARGS_NAME = {
      "path", "folder",
      "short_filename", "extension", "method"
    };
    
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < ARGS_NAME.length) {
          throw new IllegalArgumentException(usage());
        }
        String path = args[0];
        String shortFilename = args[1];
        String extension = args[2].toLowerCase().trim();
        String method = args[3].toLowerCase().trim();
        if (Extension.valueOf(extension) == null
            || ExtractionMethod.valueOf(method) == null) {
          throw new IllegalArgumentException(usage());
        }
        
        QueryExecutor queryExecutor = new QueryExecutor();
        DataFiller dataFiller = new DataFiller();
        
        Optional<String> s = queryExecutor.getArticlesFromFile(path, shortFilename, extension);
        List<String> l = XmlParser.parseResultQueryAsList(s);
        dataFiller.fillArticlesList(l);
        List<PubmedArticle> articles = dataFiller.getArticles();
        
        if (method.equals(ExtractionMethod.ALL.toString())) {
            FileGenerator.fileUnindexed(path, extension.toString(), articles);
        } else if (method.equals(ExtractionMethod.TITLE.toString())) {
            FileGenerator.fileWithTitleContent(path, extension.toString(), articles);
        } else if (method.equals(ExtractionMethod.ABSTRACT.toString())) {
            FileGenerator.fileWithAbstractContent(path, extension.toString(), articles);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    private static String usage() {
      StringBuilder usage = new StringBuilder("Usage : App ");
      Arrays.asList(ARGS_NAME).stream()
        .forEach(s -> {
          usage.append("<" + s + ">");
        });
      return usage.toString();
    }
    
    enum Extension {
      XML, JSON;
      
      @Override
      public String toString() {
        return name().toLowerCase();
      }
    }
    
    enum ExtractionMethod {
      ALL, TITLE, ABSTRACT;
      
      @Override
      public String toString() {
        return name().toLowerCase();
      }
    }
}
