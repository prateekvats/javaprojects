package com.proofpoint.filesystemproject.tests;

import com.proofpoint.filesystemproject.enums.CommonEnums;
import com.proofpoint.filesystemproject.operations.FileSystem;
import org.junit.Assert;
import org.junit.Test;

public class UnitTests implements CommonEnums {


    @Test
    public void testCreateMethod_VERIFY_CREATION() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("t1", Type.TextFile, "drive1\\f1");
            f.create("z1", Type.TextFile, "drive1\\f1");

        } catch (Exception e) {
            Assert.fail("Creation test failed:" + e.getMessage());
        }

    }

    @Test
    public void testCreateMethod_PARENT_NOT_EXISTS() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("t1", Type.TextFile, "drive1\\f1");
            f.create("z1", Type.ZipFile, "drive1\\f2");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }

    }

    @Test
    public void testCreateMethod_FILE_ALREADY_EXISTS() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("t1", Type.TextFile, "drive1\\f1");
            f.create("t1", Type.TextFile, "drive1\\f1");
            Assert.fail("Test failed.");

        } catch (Exception e) {
        }
    }

    @Test
    public void testDeleteMethod_FILE_NOT_EXISTS() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.delete("drive1\\f2");
            Assert.fail("Test failed.");
        } catch (Exception e) {

        }
    }

    @Test
    public void testDeleteMethod_VALID_DELETE_ONE_FILE() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.delete("drive1\\f1");
            if (f.exists("drive\\f1"))
                Assert.fail("Test failed. File still exists.");

        } catch (Exception e) {
            Assert.fail("Test failed.Exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteMethod_VALID_DELETE_CHILDREN() {

        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1\\f1");
            f.create("t1", Type.TextFile, "drive1\\f1\\f2");

            f.delete("drive1\\f1");
            if (f.exists("drive\\f1") || f.exists("drive\\f1\\f2") || f.exists("drive\\f1\\f2") || f.exists("drive\\f1\\f2\\t1"))
                Assert.fail("Test failed. File still exists.");

        } catch (Exception e) {
            Assert.fail("Test failed.Exception: " + e.getMessage());
        }
    }

    @Test
    public void testMoveMethod_PATH_NOT_FOUND_SRC() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.move("drive1\\f2", "drive\\f1");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }
    }

    @Test
    public void testMoveMethod_PATH_NOT_FOUND_DEST() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.move("drive1\\f1", "drive\\f2");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }
    }

    @Test
    public void testMoveMethod_ILLEGAL_OPERATION() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1");
            f.create("t1", Type.TextFile, "drive1\\f2");
            f.move("drive1\\f1", "drive\\t2");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }

        try {
            f.create("drive1", Type.Drive, "");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1");
            f.create("t1", Type.TextFile, "drive1\\f2");
            f.move("drive1", "drive1\\t2");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }
    }

    @Test
    public void testMoveMethod_VALID_MOVE() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f0", Type.Folder, "drive1");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1\\f1");
            f.create("f3", Type.Folder, "drive1\\f1\\f2");
            f.create("t1", Type.TextFile, "drive1\\f1\\f2\\f3");
            f.move("drive1\\f1\\f2", "drive1\\f0");

            if (!(f.exists("drive1\\f0\\f2") || f.exists("drive1\\f0\\f2\\f3") || f.exists("drive1\\f0\\f2\\f3\\t1")))
                Assert.fail("Test failed.");

        } catch (Exception e) {
            Assert.fail("Test failed.Exception: " + e.getMessage());
        }

    }

    @Test
    public void testWriteToFile_NON_TEXT_FILE() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f0", Type.Folder, "drive1");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1\\f1");
            f.create("f3", Type.Folder, "drive1\\f1\\f2");
            f.create("t1", Type.TextFile, "drive1\\f1\\f2\\f3");
            f.writeToFile("drive1\\f0", "Adding new content.");
            Assert.fail("Test failed.");

        } catch (Exception e) {

        }
    }

    @Test
    public void testWriteToFile_TEXT_FILE() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f0", Type.Folder, "drive1");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1\\f1");
            f.create("f3", Type.Folder, "drive1\\f1\\f2");
            f.create("t1", Type.TextFile, "drive1\\f1\\f2\\f3");
            String content = "Adding new Content";
            f.writeToFile("drive1\\f1\\f2\\f3\\t1", content);


        } catch (Exception e) {
            Assert.fail("Writing to test failed:" + e.getMessage());
        }
    }

    @Test
    public void testSizeChanges() {
        FileSystem f = new FileSystem();
        try {
            f.create("drive1", Type.Drive, "");
            f.create("f0", Type.Folder, "drive1");
            f.create("f1", Type.Folder, "drive1");
            f.create("f2", Type.Folder, "drive1\\f1");
            f.create("f3", Type.Folder, "drive1\\f1\\f2");
            f.create("t1", Type.TextFile, "drive1\\f1\\f2\\f3");
            f.create("t2", Type.TextFile, "drive1\\f0");

            String content = "1234567891";
            f.writeToFile("drive1\\f1\\f2\\f3\\t1", content);
            f.writeToFile("drive1\\f0\\t2", content);
            f.create("z1", Type.ZipFile, "drive1\\f1\\f2\\f3");
            f.create("eas1", Type.TextFile, "drive1\\f1\\f2\\f3\\z1");
            f.writeToFile("drive1\\f1\\f2\\f3\\z1\\eas1", content);
            f.create("eas2", Type.TextFile, "drive1\\f1\\f2\\f3\\z1");
            f.writeToFile("drive1\\f1\\f2\\f3\\z1\\eas2", content);

            if(f.getFileSize("drive1")!=30 || f.getFileSize("drive1\\f0")!=10 || f.getFileSize("drive1\\f1\\f2\\f3\\z1")!=10)
                Assert.fail("Test failed.");


        } catch (Exception e) {
            Assert.fail("Test failed:" + e.getMessage());
        }
    }

}
