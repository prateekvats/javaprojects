package com.proofpoint.filesystemproject.classes;

import java.util.List;

public class ZipFile extends File {

    List<Entity> contents;

    public ZipFile(String name, List<Entity> zippedContents, Entity parent) {
        super(name, Type.ZipFile, parent);
        this.contents = zippedContents;
        getSize();
    }


    public long getSize() {
        long totalSize = 0;
        for (Entity e : contents) {
            totalSize += e.getSize();
        }
        totalSize = totalSize / 2;
        this.Size = totalSize;
        return this.Size;
    }

    public void addContent(Entity e)  {
        contents.add(e);
    }
}
