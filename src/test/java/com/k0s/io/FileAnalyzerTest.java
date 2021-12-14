package com.k0s.io;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import com.k0s.io.fileanalyzer.FileAnalyzer;
import com.k0s.io.fileanalyzer.FileAnalyzerLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileAnalyzerTest {
    static String testFile = "src/main/resources/test.txt";
    static String emptyTestFile = "src/main/resources/emptyFile.txt";

    @BeforeAll
    public static void allocateFile(){
        File emptyFile = new File(emptyTestFile);
        try {
            emptyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writesToFile = new FileWriter(testFile)){

            BufferedWriter writer = new BufferedWriter(writesToFile);

            String string = "Антошка, Антошка!\n" +
                    "Пойдем копать картошку!\n" +
                    "Антошка, Антошка!\n" +
                    "Пойдем копать картошку!\n" +
                    "\n" +
                    "Тили — тили, трали — вали,\n" +
                    "Это мы не проходили,\n" +
                    "Это нам не задавали.\n" +
                    "Тарам-пам-пам!\n" +
                    "Тарам-пам-пам!\n" +
                    "\n" +
                    "Антошка, Антошка!\n" +
                    "Сыграй нам на гармошке!\n" +
                    "Антошка, Антошка!\n" +
                    "Сыграй нам на гармошке!\n" +
                    "\n" +
                    "Тили — тили, трали — вали,\n" +
                    "Это мы не проходили,\n" +
                    "Это нам не задавали.\n" +
                    "Тарам-пам-пам!\n" +
                    "Тарам-пам-пам!\n" +
                    "\n" +
                    "Антошка, Антошка!\n" +
                    "Готовь к обеду ложку!\n" +
                    "Антошка, Антошка!\n" +
                    "Готовь к обеду ложку!\n" +
                    "\n" +
                    "Тили — тили, трали — вали,\n" +
                    "Это, братцы, мне по силе,\n" +
                    "Откажусь теперь едва ли!\n" +
                    "Тарам-пам-пам!\n" +
                    "Тарам-пам-пам!";
            writer.write(string);
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @AfterAll
    static void clean() throws IOException {
        FileManager.remove(testFile);
        FileManager.remove(emptyTestFile);
    }


    @Test
    @DisplayName("Test FileAnalyzerCountWords")
    public void wordCount() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(testFile, "Антошка");
        assertEquals(12, fileAnalyzer.getCountWords());
    }

    @Test
    @DisplayName("Test FileAnalyzerCountSentence")
    public void sentenceCount() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(testFile, "Антошка");
        assertEquals(6, fileAnalyzer.getCountSentences());
    }

    @Test
    @DisplayName("Test FileAnalyzerGetList")
    public void sentenceList() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(testFile, "Антошка");
        assertEquals(6, fileAnalyzer.getSentenceList().size());
    }

    @Test
    @DisplayName("Test FileAnalyzer methods")
    public void diffWordsTest() throws IOException {

        FileAnalyzer fileAnalyzer = new FileAnalyzer();

        assertEquals(6, fileAnalyzer.getSentenceList(testFile, "Антошка").size());

        assertEquals(6, fileAnalyzer.getCountWords(testFile, "Тили" ));

        assertEquals(0, fileAnalyzer.getCountWords(emptyTestFile, "Тили" ));

        assertEquals(12, fileAnalyzer.getCountWords(testFile, "пам" ));

    }


    @Test
    @DisplayName("Test Launcher")
    public void defaultLaunch()

    {
        String[] args = {testFile, "Антошка"};
        FileAnalyzerLauncher.main(args);
    }

    @Test
    @DisplayName("Test Launcher with non-existent keyword")
    public void keywordNotFound()
    {
        String[] args = {testFile, "111"};
        FileAnalyzerLauncher.main(args);
    }

    @Test
    @DisplayName("Test Launcher with empty file")
    public void emptyFileExit0()
    {
        String[] args = {emptyTestFile, "Антошка"};
        FileAnalyzerLauncher.main(args);
    }

    @Test
    @DisplayName("Test Launcher with different arguments")
    @ExpectSystemExitWithStatus(-1)
    public void wrongArgsExit1()
    {
        String[] args = {testFile, "kljkl", "dfdf"};
        FileAnalyzerLauncher.main(args);
    }

    @Test
    @DisplayName("Test method with NULL file")
    @ExpectSystemExitWithStatus(-1)
    public void nullArgsExit1()

    {
        FileAnalyzerLauncher.main(null);
    }

    @Test
    @DisplayName("Test method with non-existent file")
    public void wrongFilePath()
    {
        String[] args = {"test8908098098.txt", "111"};
        FileAnalyzerLauncher.main(args);
    }


}
