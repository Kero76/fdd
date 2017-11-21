package fr.rouen.univ.models;

public class FileContentMesh extends FileContent {

    private String meshes;

    public FileContentMesh(String pmid, String title, String content, String meshes) {
        super(pmid, title, content);
        this.meshes = meshes;
    }

    public String getMeshes() {
        return meshes;
    }

    public void setMeshes(String meshes) {
        this.meshes = meshes;
    }

    @Override
    public String toString() {
        return "FileContentMesh{" + "pmid='" + super.getPmid() + '\'' + ", title='" + super.getTitle() + '\'' + ", content='" + super.getContent() + '\'' + "meshes='" + meshes + '\'' + '}';
    }
}
