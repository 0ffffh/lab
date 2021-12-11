package com.k0s.io;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.nio.charset.StandardCharsets.UTF_8;


public class FileAnalyzer {
    public static void main(String[] args)  {
        if(args == null || args.length != 2) {
            System.out.println("Usage: FileAnalyzer <path to file> <keyword>");
            System.exit(-1);
        }
        initAnalyzer(args[0], args[1]);
    }
    private static void initAnalyzer(String path, String keyword){
        if(path != null){
            File sourceFile = new File(path);

            if ( sourceFile.isFile() &&  sourceFile.canRead()){
                if (sourceFile.length() == 0){
                    System.out.println("File " + sourceFile + " is empty");
                } else {
                    printResult(searchWordPattern(sourceFile, keyword), keyword);
                }
            }
        } else {
            System.out.println("Can't read file < " + path + " >");
        }
    }

    private static void printResult(int count, String keyWord) {
        if (count > 0) {
            System.out.println("Text contains word \"" + keyWord + "\" " + count + " times");
        } else {
            System.out.println("Keyword not found");
        }
    }


    private static int searchWordPattern(File sourceFile, String keyWord) {
        int countKeywords = 0;
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile.getAbsolutePath()), UTF_8))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(keyWord.toLowerCase());
                Matcher matcher = pattern.matcher(line.toLowerCase());

                if (matcher.find()) {
                    countKeywords++;
                    System.out.println(line);
                    while (matcher.find()) {
                        countKeywords++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Can't open " + sourceFile );
            e.printStackTrace();
        }
        return countKeywords;
    }
}
