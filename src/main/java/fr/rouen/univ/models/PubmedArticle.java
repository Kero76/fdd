package fr.rouen.univ.models;

import java.util.List;

public class PubmedArticle {

    /**
     * PMID of the article.
     */
    String pmid;

    /**
     * Title of the article.
     */
    String titleText;

    /**
     * Abstract of the title.
     */
    String abstractText;

    /**
     * List of all meshes available for the article.
     */
    List<Mesh> meshes;

    public PubmedArticle() {}

    public PubmedArticle(String pmid, String titleText, String abstractText, List<Mesh> meshes) {
        this.pmid = pmid;
        this.titleText = titleText;
        this.abstractText = abstractText;
        this.meshes = meshes;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(List<Mesh> meshes) {
        this.meshes = meshes;
    }

    @Override
    public String toString() {
        return "PubmedArticle{" + "pmid='" + pmid + '\'' + ", titleText='" + titleText + '\'' + ", abstractText='" + abstractText + '\'' + ", meshes=" + meshes + '}';
    }
}
