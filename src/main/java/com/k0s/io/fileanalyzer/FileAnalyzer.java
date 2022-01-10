package com.k0s.io.fileanalyzer;

import java.io.*;


public class FileAnalyzer {
    public static void main(String[] args) {
        if (args == null || args.length != 2 ) {
            System.out.println("Usage: FileAnalyzer <path to file> <keyword>");
            return;
        }
        try {
            initAnalyzer(args[0], args[1]);
        } catch (IOException e) {
            System.out.println("Can't read file <" + args[0] + "> " + e);
        }
    }

    private static void initAnalyzer(String path, String keyword) throws IOException {
        Analyzer analyzer = new Analyzer(path, keyword);
        System.out.println(analyzer);
    }
}

