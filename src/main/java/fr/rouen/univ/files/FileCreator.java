package fr.rouen.univ.files;

import fr.rouen.univ.models.PubmedArticle;
import fr.rouen.univ.parser.XmlParser;

import java.util.ArrayList;
import java.util.List;

public class FileCreator {

    private List<PubmedArticle> articles;

    public FileCreator() {
        this.articles = new ArrayList<>();
    }

    /**
     * Fill the list of articles with information get from a list of string.
     *
     * @param articles
     *  A list who contain all information from the list.
     */
    public void fillArticlesList(List<String> articles) {
        PubmedArticle article = null;
        for (String s : articles) {
            if (s.contains("article")) {
                if (article != null && article.getPmid() != null) {
                    this.articles.add(article);
                }
                article = new PubmedArticle();
            }
            if (s.contains("PMID")) {
                article.setPmid(XmlParser.parseLine(s));
            } else if (s.contains("ArticleTitle")) {
                article.setTitleText(XmlParser.parseLine(s));
            } else if (s.contains("AbstractText")) {
                article.setAbstractText(XmlParser.parseLine(s));
            }
        }
    }

    public List<PubmedArticle> getArticles() {
        return articles;
    }
}
