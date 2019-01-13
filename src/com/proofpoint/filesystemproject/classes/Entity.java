package com.proofpoint.filesystemproject.classes;

import com.proofpoint.filesystemproject.enums.CommonEnums;


public abstract class Entity implements CommonEnums {

    public Type EntityType;
    public String Name;
    public String Path;
    protected long Size;

    protected Entity Parent;


    public String getEntityName() {
        return Name;
    }

    public Entity getParent() {
        return Parent;
    }

    public void setParent(Entity e) {
        this.Parent = e;
    }

    public abstract long getSize();

    protected String setEntityPath() {

        return (getParent() != null ? getParent().Path + "\\" : "") + getEntityName();

    }



}
