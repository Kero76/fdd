package fr.rouen.univ.models;

import java.util.List;

public class Mesh {

    /**
     * Mesh description.
     */
    String description;

    /**
     * List of all qualifier for the description.
     * It must be empty.
     */
    List<String> qualifiers;

    /**
     * Mesh constructor.
     *
     * @param description
     *  Descrption of the mesh.
     * @param qualifiers
     *  Qualifiers of the mesh.
     */
    public Mesh(String description, List<String> qualifiers) {
        this.description = description;
        this.qualifiers = qualifiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<String> qualifiers) {
        this.qualifiers = qualifiers;
    }
}
