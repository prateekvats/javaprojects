package com.proofpoint.filesystemproject.classes;

public class TextFile extends File {

    String Content = "";

    public TextFile(String name, Entity parent, String content) {
        super(name, Type.TextFile, parent);
        this.Content = content;
        getSize();
    }


    public void addContent(String content) {
        this.Content += content;
    }

    public void updateContent(String content) {
        this.Content = content;
    }

    @Override
    public long getSize() {
        this.Size = this.Content.length();
        return this.Size;
    }
}
