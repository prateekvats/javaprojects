package com.proofpoint.filesystemproject.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Drive extends Entity {

    private List<Entity> content;

    public Drive(String name) {
        this.Name = name;
        this.EntityType = Type.Drive;
        this.content = new ArrayList<>();
        this.Parent = null;
        this.Path = this.setEntityPath();
        getSize();
    }

    public String getEntityName() {
        return Name;
    }

    public Entity getParent() {
        return Parent;
    }

    public long getSize() {
        long totalSize = 0;
        for (Entity e : content) {
            totalSize += e.getSize();
        }
        this.Size = totalSize;
        return this.Size;
    }

    public void addContent(Entity e) {
        content.add(e);
    }

    public void addContents(List<Entity> e) {
        content.addAll(e);
    }


    public List<String> getFiles() {
        return content.stream().map(e -> e.Name).collect(Collectors.toList());
    }
}
