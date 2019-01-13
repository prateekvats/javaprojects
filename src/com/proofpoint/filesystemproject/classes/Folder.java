package com.proofpoint.filesystemproject.classes;

import com.proofpoint.filesystemproject.enums.CommonEnums;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Folder extends NonDrive {


    private List<Entity> content;

    public Folder(String name, Entity parent) {
        super(name, CommonEnums.Type.Folder, parent);
        content = new ArrayList<>();
        getSize();
    }

    public void addContent(Entity e) {
        content.add(e);
    }

    public List<String> getFiles() {
        return content.stream().map(e -> e.Name).collect(Collectors.toList());
    }

    public void addContents(List<Entity> e) {
        content.addAll(e);
    }

    public List<Entity> getContents() {
        return content;
    }

    public long getSize() {
        long totalSize = 0;
        for (Entity e : content) {
            totalSize += e.getSize();
        }
        this.Size = totalSize;
        return this.Size;
    }
}
