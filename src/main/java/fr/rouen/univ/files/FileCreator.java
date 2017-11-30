package fr.rouen.univ.files;

import fr.rouen.univ.models.Mesh;
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
        List<String> meshHeadingContent = new ArrayList<>();
        for (String s : articles) {
            // If we encounter a tag <article> ...
            if (s.contains("article")) {
                // ... we check the pmid and if different to null ...
                if (article != null && article.getPmid() != null) {
                    // Set meshes thanks to private method buildMeshContent and clear list who contains meshHeading string.
                    article.setMeshes(this.buildMeshContent(article, meshHeadingContent));
                    meshHeadingContent.clear();
                    // ... we add the article on list, ..
                    this.articles.add(article);
                }
                // ... else we create new instance of PubmedArticle.
                article = new PubmedArticle();
            }
            if (s.contains("PMID")) {
                article.setPmid(XmlParser.parseLine(s));
            } else if (s.contains("ArticleTitle")) {
                article.setTitleText(XmlParser.parseLine(s));
            } else if (s.contains("AbstractText")) {
                article.setAbstractText(XmlParser.parseLine(s));
            } else if(s.contains("DescriptorName") || s.contains("QualifierName")) {
                meshHeadingContent.add(XmlParser.parseLine(s));
            }
        }
    }

    private List<Mesh> buildMeshContent(PubmedArticle article, List<String> meshHeadingContent) {
        // Create a list of mesh ...
        // ... and loop on each String of meshHeadingContent and build Mesh content.
        List<Mesh> meshes = new ArrayList<>();
        for (String s : meshHeadingContent) {
            Mesh mesh = new Mesh();

            // If the word is in capitalize, then s is a DescriptorName ...
            if (Character.isUpperCase(s.charAt(0))) {
                // Insert the mesh only if the DescriptorName is already defined.
                if (mesh.getDescription() != null) {
                    meshes.add(mesh);
                }
                mesh.setDescription(s);
            }
        }

        // To complete process, return meshes to insert on article.
        return meshes;
    }

    public List<PubmedArticle> getArticles() {
        return articles;
    }
}
