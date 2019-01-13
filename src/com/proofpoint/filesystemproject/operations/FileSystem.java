package com.proofpoint.filesystemproject.operations;

import com.proofpoint.filesystemproject.classes.*;
import com.proofpoint.filesystemproject.enums.CommonEnums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FileSystem implements CommonEnums {

    List<Entity> files;


    public FileSystem() {
        files = new ArrayList<Entity>();
    }

    /**
     * @param name       The name of the file you're trying to create.
     * @param type       The type of file you're trying to create. Accepts the enum Type.
     * @param parentPath The parent Path of the file you're trying to create.
     * @throws Exception
     */
    public void create(String name, Type type, String parentPath) throws Exception {

        String newFilePath = parentPath + (type != Type.Drive ? "\\" : "") + name;

        Entity newFile = getEntityByPath(newFilePath);


        if (newFile != null)
            throw new Exception("File you're trying to create already exists.");

        Entity parent = getEntityByPath(parentPath);

        if (parent == null && type != Type.Drive)
            throw new Exception("The location where you're trying to create the " + type + " doesn't exist.");


        if (type == Type.Drive)
            newFile = new Drive(name);
        else if (type == Type.Folder) {
            newFile = new Folder(name, parent);
        } else if (type == Type.TextFile)
            newFile = new TextFile(name, parent, "");
        else if (type == Type.ZipFile)
            newFile = new ZipFile(name, new ArrayList<Entity>(), parent);
        else
            throw new Exception("An exception occured while creating the entity: " + name);

        if (type != Type.Drive) {
            if (parent.EntityType == Type.Drive)
                ((Drive) parent).addContent(newFile);
            else if (parent.EntityType == Type.Folder)
                ((Folder) parent).addContent(newFile);
            else if (parent.EntityType == Type.ZipFile)
                ((ZipFile) parent).addContent(newFile);
            else
                throw new Exception("Invalid operation.");
        }
        files.add(newFile);
        percolateSizeChange(newFile);

    }

    /**
     * @param filePath - This is the Path of the file you're trying to delete.
     * @throws IOException
     */
    public void delete(String filePath) throws IOException {
        Entity file = getEntityByPath(filePath);


        if (file != null) {
            List<Entity> entitiesToBeDeleted = getEntitiesByPath(file.Path);

            if (file.EntityType == Type.Drive || file.EntityType == Type.Folder || file.EntityType == Type.ZipFile)
                files.removeAll(entitiesToBeDeleted);

            Entity parent = file.getParent();

            files.remove(file);
            percolateSizeChange(parent);


        } else
            throw new IOException("Path not found!");

    }

    /**
     * @param path - This is the path of the file you want to add content to.
     * @param content
     * @throws IOException
     */
    public void writeToFile(String path, String content) throws IOException {
        Entity file = getEntityByPath(path);
        TextFile tf;

        if (file == null)
            throw new IOException("Path not found.");

        if (file.EntityType != Type.TextFile)
            throw new IOException("This is not a text file!");
        else
            tf = (TextFile) file;


        tf.updateContent(content);
        percolateSizeChange(file);
    }

    /**
     * @param src - This is the source path of the file/folder you're trying to move
     * @param dst - This is the destination path of folder/drive you want the source file/folder to be
     * @throws Exception
     */
    public void move(String src, String dst) throws Exception {

        Entity file = getEntityByPath(src);
        Entity dest = getEntityByPath(dst);

        if (dest == null)
            throw new Exception("Dest Path does not exist.");

        if (src == null)
            throw new Exception("Src Path does not exist.");

        if (!(dest.EntityType == Type.Drive || dest.EntityType == Type.Folder || file.EntityType == Type.Drive))
            throw new Exception("Illegal operation.");

        ((Folder) dest).addContent(file);
        moveR(src, dst);
        percolateSizeChange(file);

    }

    /* Recursive helper function to assist with moving operation*/
    private void moveR(String source, String destination) throws Exception {
        Entity src = getEntityByPath(source);
        Entity dest = getEntityByPath(destination);

        if (src.EntityType == Type.Folder) {
            src.setParent(dest);
            src.Path = dest.Path + "\\" + src.Name;


            for (Entity file : ((Folder) src).getContents()) {
                moveR(file.Path, src.Path);
            }

        } else {

            src.setParent(dest);
            src.Path = dest.Path + "\\" + src.Name;
        }

    }

    /**
     * @param path - Takes in the path of the file/folder/drive
     * @return - True if path exists, False if it doesnt.
     */

    public boolean exists(String path) {
        return getEntityByPath(path) != null;
    }


    /**
     * @param path Takes in the path of the file
     * @return The size of the folder/file/directory
     */
    public long getFileSize(String path) throws Exception {
        Entity e;
        if(!exists(path))
            throw new Exception("The path of the file is incorrect.");
        else{
            e = getEntityByPath(path);
        }

        return e.getSize();

    }
    private Entity getEntityByPath(String path) {

        List<Entity> e = files.stream().filter(x -> x.Path.toLowerCase().equals(path.toLowerCase())).collect(Collectors.toList());

        return (e.size() == 0) ? null : e.get(0);

    }

    private List<Entity> getEntitiesByPath(String path) {

        return files.stream().filter(x -> x.Path.toLowerCase().contains(path.toLowerCase())).collect(Collectors.toList());

    }

    private void percolateSizeChange(Entity e){

        while(e!=null) {
            e.getSize();
            e = e.getParent();
        }
    }


}
