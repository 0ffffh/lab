package com.k0s.io;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class FileAnalyzerTest {

    @BeforeAll
    public static void allocateFile(){
        File emptyFile = new File("emptyFile.txt");
        try {
            emptyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writesToFile = new FileWriter("test.txt")){

            BufferedWriter writer = new BufferedWriter(writesToFile);

            String string = "89Антошка98, Антошка!\n" +
                    "Пойдем копать АнТошкаАНТОШКААнтошка картошку!\n" +
                    "(Антошка), Антошка!\n" +
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
        FileManager.remove("test.txt");
        FileManager.remove("emptyFile.txt");
    }


    @Test
    @DisplayName("Test method with worked parameters")
    public void defaultLaunch()

    {
        String[] args = {"test.txt", "Антошка"};
        FileAnalyzer.main(args);
    }

    @Test
    @DisplayName("Test method with non-existent keyword")
    public void keywordNotFound()
    {
        String[] args = {"test.txt", "111"};
        FileAnalyzer.main(args);
    }

    @Test
    @DisplayName("Test method with empty file")
    public void emptyFileExit0()
    {
        String[] args = {"emptyFile.txt", "Антошка"};
        FileAnalyzer.main(args);
    }

    @Test
    @DisplayName("Test method with different count arguments")
    @ExpectSystemExitWithStatus(-1)
    public void wrongArgsExit1()
    {
        String[] args = {"test.txt", "kljkl", "dfdf"};
        FileAnalyzer.main(args);
    }

    @Test
    @DisplayName("Test method with NULL file")
    @ExpectSystemExitWithStatus(-1)
    public void nullArgsExit1()

    {
        FileAnalyzer.main(null);
    }

    @Test
    @DisplayName("Test method with non-existent file")
    public void wrongFilePath()
    {
        String[] args = {"test8908098098.txt", "111"};
        FileAnalyzer.main(args);
    }


}
