package fr.rouen.univ.models;

public class FileContent {

    private String pmid;
    private String title;
    private String content;

    public FileContent(String pmid, String title, String content) {
        this.pmid = pmid;
        this.title = title;
        this.content = content;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileContent{" + "pmid='" + pmid + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }
}
