package fr.rouen.univ.models;

import java.util.List;

public class Mesh {
    private String ui;

    private String descriptorName;

    private List<String> qualifiersName;

    public Mesh(String ui, List<String> qualifiersName) {
        this.ui = ui;
        this.qualifiersName = qualifiersName;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public List<String> getQualifiersName() {
        return qualifiersName;
    }

    public void setQualifiersName(List<String> qualifiersName) {
        this.qualifiersName = qualifiersName;
    }
}
