package com.k0s.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Random;

public class FileManagerTest {

    static String Path = "src/main/resources/Test";
    static String PathToCopyFolder = "src/main/resources/fmTest-COPY";
    static String PathToMoveFolder = "src/main/resources/fmTest-MOVED";


    @BeforeEach
      void initResources() throws IOException {

        File root = new File(Path);
        root.mkdir();

        for (int i = 0; i < 5; i++) {

            File dir = new File(Path, String.valueOf(i));
            dir.mkdir();
            File file = new File(Path,i + ".tmp");
            file.createNewFile();
            allocateFile(file);
            for (int j = 0; j < 5; j++) {
                File dirIn = new File(dir.getAbsolutePath(), String.valueOf(j));
                dirIn.mkdir();

                File fileIn = new File(dirIn.getParent(),j + ".tmp");
                fileIn.createNewFile();
                allocateFile(fileIn);
            }

        }
    }
    public static void allocateFile(File file){
        try ( FileWriter writesToFile = new FileWriter(file)){

            BufferedWriter writer = new BufferedWriter(writesToFile);
            int line;
            Random rand = new Random();
            for (int i = 0; i < rand.nextInt(100); i++) {
                line = rand.nextInt(50000);
                writer.write(line + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    @AfterAll
    static void clearResources() throws IOException {

        File path = new File(Path);
        File pathToCopyFolder = new File(PathToCopyFolder);
        File pathToMoveFolder = new File(PathToMoveFolder);

        if (path.exists()){
            FileManager.remove(Path);
        }
        if (pathToCopyFolder.exists()){
            FileManager.remove(PathToCopyFolder);
        }
        if (pathToMoveFolder.exists()){
            FileManager.remove(PathToMoveFolder);
        }
    }

    String getNonAccessFolder(){
        String rootPath;
        if (System.getProperty("os.name").equals("Windows")){
             rootPath = "C:/Windows";
        } else {
             rootPath = "/root";
        }
        return rootPath;
    }





    @Test
    @DisplayName("Count files in directory")
    public void countFilesTest() throws IOException {
        assertEquals(30, FileManager.countFiles(Path));
    }


    @Test
    @DisplayName("Count files in non-existing directory. Throw IOException")
    public void countFilesIfNotExistsTest()  {
        assertThrows(IOException.class, ()-> FileManager.countFiles(Path + "fxffdgdfgdf"));
    }

    @Test
    @DisplayName("Count files in NULL. Throw IllegalArgumentException")
    public void countFilesNullPathTest(){
        assertThrows(IllegalArgumentException.class, ()-> FileManager.countFiles(null));
    }

    @Test
    @DisplayName("Count files in access denied directory. Throw IOException")
    public void countFileNonAccessFolderTest() {
        assertThrows(IOException.class, ()-> FileManager.countFiles(getNonAccessFolder()));
    }
    @Test
    @DisplayName("Count files in one file")
    public void countFileIsFileTest() throws IOException {
        assertEquals(-1, FileManager.countDirs(Path + "/0.tmp"));
    }

    @Test
    @DisplayName("Count subdirectories in directory")
    public void countDirTest() throws IOException {
        assertEquals(30, FileManager.countDirs(Path));
    }

    @Test
    @DisplayName("Count directories in non-existing directory. Throw IOException")
    public void countDirIfNotExistsTest(){
        assertThrows(IOException.class, ()-> FileManager.countDirs(Path + "fxffdgdfgdf"));
    }

    @Test
    @DisplayName("Count subdirectories in NULL. Throw IllegalArgumentException")
    public void countDirNullPathTest(){
        assertThrows(IllegalArgumentException.class, ()-> FileManager.countDirs(null));
    }

    @Test
    @DisplayName("Count directories in access denied directory. Throw IOException")
    public void countDirNonAccessFolderTest(){
        assertThrows(IOException.class, ()-> FileManager.countDirs(getNonAccessFolder()));
    }

    @Test
    @DisplayName("Count directories in file. Throw IOException")
    public void countDirIsFileTest() throws IOException {
        assertEquals(-1, FileManager.countDirs(Path + "/0.tmp"));
    }


    @Test
    @DisplayName("Copy files")
    public void copyFileTest() throws IOException {
        for (int i = 0; i < 5; i++) {
            File inFile = new File(Path,i+".tmp");
            File outFile = new File(Path, i+"-copyed-from-file-to-copy.tmp");
            FileManager.copy(inFile.getAbsolutePath(), outFile.getAbsolutePath());

            long actualSize = inFile.length();
            long expectedSize = outFile.length();

            assertEquals(expectedSize, actualSize);
        }
    }

    @Test
    @DisplayName("Copy folders")
    public void copyTest() throws IOException {

        File inFile = new File(Path);
        File outFile = new File(PathToCopyFolder);
        FileManager.copy(inFile.getAbsolutePath(), outFile.getAbsolutePath());

        int actualFileCount = FileManager.countFiles(Path);
        int expectedFileCount = FileManager.countFiles(PathToCopyFolder);

        int actualDirCount = FileManager.countDirs(Path);
        int expectedDirCount = FileManager.countDirs(PathToCopyFolder);

        long actualDirSize = FileManager.getSize(Path);
        long expectedDirSize = FileManager.getSize(PathToCopyFolder);

        assertEquals(expectedFileCount, actualFileCount);
        assertEquals(expectedDirCount, actualDirCount);
        assertEquals(expectedDirSize, actualDirSize);
    }

    @Test
    @DisplayName("Copy to NULL. Throw IllegalArgumentException")
    public void copyFolderNullSourceTest() {
        assertThrows(IllegalArgumentException.class, ()->{
            File outFile = new File(PathToCopyFolder);
            FileManager.copy(null, outFile.getAbsolutePath());
        });
    }

    @Test
    @DisplayName("Copy from NULL. Throw IllegalArgumentException")
    public void copyFolderNullDestinationTest() {
        assertThrows(IllegalArgumentException.class, ()->{
            File inFile = new File(Path);
            FileManager.copy(inFile.getAbsolutePath(), null);
        });
    }

    @Test
    @DisplayName("Copy to non access folder. Throw IOException")
    public void copyToNonAccessFolderTest() {
        File inFile = new File(Path);
        assertThrows(IOException.class, ()-> FileManager.copy(inFile.getAbsolutePath(), getNonAccessFolder()));
    }

    @Test
    @DisplayName("Copy from non access folder. Throw IOException")
    public void copyFromNonAccessFolderTest() {
        assertThrows(IOException.class, ()->{
            File outFile = new File(PathToCopyFolder);
            FileManager.copy(getNonAccessFolder(),outFile.getAbsolutePath());
        });
    }

    @Test
    @DisplayName("Copy folder to file test. Throw IO Exception")
    public void copyFolderToFileTest()  {
        File inFile = new File(Path);
        assertThrows(IOException.class, ()-> FileManager.copy(inFile.getAbsolutePath(), PathToCopyFolder + "/0.tmp"));
    }

    @Test
    @DisplayName("Move test")
    public void moveTest() throws IOException {

        File copyFile = new File(PathToCopyFolder);
        File moveFile = new File(PathToMoveFolder);

        FileManager.copy(Path, PathToCopyFolder);

        long actualFileCount = FileManager.countFiles(PathToCopyFolder);
        long actualDirCount = FileManager.countDirs(PathToCopyFolder);
        long actualDirSize = FileManager.getSize(PathToCopyFolder);

        FileManager.move(copyFile.getAbsolutePath(), moveFile.getAbsolutePath());

        long expectedFileCount = FileManager.countFiles(PathToMoveFolder);
        long expectedDirCount = FileManager.countDirs(PathToMoveFolder);
        long expectedDirSize = FileManager.getSize(PathToMoveFolder);

        assertEquals(expectedFileCount, actualFileCount);
        assertEquals(expectedDirCount, actualDirCount);
        assertEquals(expectedDirSize, actualDirSize);
    }

    @Test
    @DisplayName("Move from NULL to folder. Throw IllegalArgumentException")
    public void moveFolderNullSourceTest() {
        assertThrows(IllegalArgumentException.class, ()->{
            File outFile = new File(PathToMoveFolder);
            FileManager.move(null, outFile.getAbsolutePath());
        });
    }


    @Test
    @DisplayName("Move from folder to NULL. Throw IllegalArgumentException")
    public void moveFolderNullDestinationTest()  {
        assertThrows(IllegalArgumentException.class, ()->{
            File inFile = new File(Path);
            FileManager.move(inFile.getAbsolutePath(), null);
        });
    }

    @Test
    @DisplayName("Move  to non access folder. Throw IOException")
    public void moveToNonAccessFolderTest() {
        File inFile = new File(Path);
        assertThrows(IOException.class, ()-> FileManager.move(inFile.getAbsolutePath(), getNonAccessFolder()));
    }

    @Test
    @DisplayName("Move from non access folder. Throw IOException")
    public void moveFromNonAccessFolderTest() {
        assertThrows(IOException.class, ()->{
            File outFile = new File(PathToCopyFolder);
            FileManager.move(getNonAccessFolder(),outFile.getAbsolutePath());
        });
    }

    @Test
    @DisplayName("Move folder to file. Throw IOException")
    public void moveFolderToFileTest() throws IOException {
        File inFile = new File(Path);
        assertThrows(IOException.class, ()-> FileManager.move(inFile.getAbsolutePath(), Path + "/0.tmp"));
    }

    @Test
    @DisplayName("Move folder to itself. Throw IOException")
    public void moveFolderToSelfTest() throws IOException {
            File inFile = new File(Path);
            FileManager.move(inFile.getAbsolutePath(), inFile.getAbsolutePath());
    }

}
