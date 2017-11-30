package fr.rouen.univ.files;

import fr.rouen.univ.models.Mesh;
import fr.rouen.univ.models.PubmedArticle;
import fr.rouen.univ.parser.XmlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileCreator {

    private List<PubmedArticle> articles;

    /**
     * Logger to check internally the process of each method.
     */
    private static Logger logger = Logger.getLogger(FileCreator.class.getName());

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
        logger.info("Enter in fillArticlesList");
        PubmedArticle article = null;
        List<String> meshHeadingContent = new ArrayList<>();
        for (String s : articles) {
            // If we encounter a tag <article> ...
            if (s.contains("article")) {
                // ... we check the pmid and if different to null ...
                if (article != null && article.getPmid() != null) {
                    // Set meshes thanks to private method buildMeshContent and clear list who contains meshHeading string.
                    logger.info(String.valueOf(meshHeadingContent));
                    List<Mesh> meshesProcess = this.buildMeshContent(meshHeadingContent);
                    article.setMeshes(meshesProcess);
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

    /**
     * Get all articles objects to create file.
     *
     * @return
     *  All Articles build.
     */
    public List<PubmedArticle> getArticles() {
        return articles;
    }

    /**
     * Private method to build Mesh from a list of string who contain information about mesh.
     *
     * @param meshHeadingContent
     *  A list with mesh information to parse on mesh.
     * @return
     *  The list of mesh object for an article.
     */
    private List<Mesh> buildMeshContent(List<String> meshHeadingContent) {
        // Create a list of mesh ...
        // ... and loop on each String of meshHeadingContent and build Mesh content.
        List<Mesh> meshes = new ArrayList<>();
        Mesh mesh = null;
        for (String s : meshHeadingContent) {
            logger.info("Current value string : " + s);
            // If the word is in capitalize, then s is a DescriptorName ...
            if (Character.isUpperCase(s.charAt(0))) {
                // Insert the mesh only if the DescriptorName is already defined or mesh is define.
                if (mesh != null && mesh.getDescription() != null) {
                    logger.info("Mesh insert : " + mesh);
                    meshes.add(mesh);
                }
                // Create new mesh and set description.
                mesh = new Mesh();
                mesh.setDescription(s);
            }
            // ... else, the string is a Qualifier.
            else {
                String[] qualifiersSplitted = s.split(",");
                for (String q : qualifiersSplitted ) {
                    logger.info("Qualifier " + q + " at insert on mesh : " + mesh);
                    mesh.addQualifier(q);
                }
            }
        }

        // To complete process, return meshes to insert on article.
        return meshes;
    }
}
