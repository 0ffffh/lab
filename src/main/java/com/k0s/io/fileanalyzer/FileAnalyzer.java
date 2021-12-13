package com.k0s.io.fileanalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {

    private int countWords;
    private List<String> sentenceList = new ArrayList<>();



    public FileAnalyzer(String filePath, String keyWord) throws IOException {
        analyze(new FileInputStream(filePath), keyWord);
    }


    public int getCountWords() {
        return countWords;
    }

    public int getCountSentences() {
        return sentenceList.size();
    }

    public List<String> getSentenceList() {
        return sentenceList;
    }


    private void analyze(InputStream inputStream, String keyWord) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineToSentence = line.split("[.!?]\\S+");
                for (String sentence : lineToSentence) {
                    if (sentence.toLowerCase().contains(keyWord.toLowerCase())) {
                        this.sentenceList.add(sentence);
                        int index = 0;
                        while (index < sentence.length() &&
                                (index = sentence.toLowerCase().indexOf(keyWord.toLowerCase(), index)) >= 0) {
                            countWords++;
                            index = index + keyWord.length();
                        }
                    }
                }
            }
        }
    }
}

