package com.proofpoint.filesystemproject.classes;


public abstract class NonDrive extends Entity {


    NonDrive(String name, Type type, Entity parent) {
        this.Name = name;
        this.EntityType = type;
        this.Parent = parent;
        this.Path = this.setEntityPath();
    }


}
