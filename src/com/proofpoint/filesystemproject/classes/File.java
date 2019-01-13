package com.proofpoint.filesystemproject.classes;

import com.proofpoint.filesystemproject.enums.CommonEnums;

public abstract class File extends NonDrive {

    public File(String name, CommonEnums.Type type, Entity parent) {
        super(name, type, parent);
    }

}
