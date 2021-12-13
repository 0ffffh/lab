package com.k0s.io.fileanalyzer;

import java.io.*;



public class FileAnalyzerLauncher {
    public static void main(String[] args) {
        if (args == null || args.length != 2 ) {
            System.out.println("Usage: FileAnalyzer <path to file> <keyword>");
            System.exit(-1);
        }
        try {
            initAnalyzer(args[0], args[1]);
        } catch (IOException e) {
            System.out.println("Can't read file <" + args[0] + ">");
            e.printStackTrace();
        }
    }

    private static void initAnalyzer(String path, String keyword) throws IOException {

            FileAnalyzer fileAnalyzer = new FileAnalyzer(path, keyword);
            System.out.println("Count words = " + fileAnalyzer.getCountWords());
            System.out.println("Count sentences = " + fileAnalyzer.getCountSentences());
            System.out.println("Sentences: ");
            for (String print : fileAnalyzer.getSentenceList()) {
                System.out.println(print);
            }
    }
}

