package com.k0s.io.fileanalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private String keyWord;
    private String source;
    private int countWords;
    private final List<String> sentenceList = new ArrayList<>();

    public Analyzer(){}

    public Analyzer(String source, String keyWord) throws IOException {
        this.keyWord = keyWord;
        this.source = source;
        FileInputStream input = new FileInputStream(source);
        analyze(input, keyWord);
    }

    public int getCountWords() {
        return countWords;
    }
    public List<String> getSentenceList() {
        return sentenceList;
    }
    public int getCountSentences() {
        return sentenceList.size();
    }


    public int getCountWords(String source, String keyWord) throws IOException {
        isNewSourceKeyword(source, keyWord);
        return getCountWords();
    }

    public List<String> getSentenceList(String source, String keyWord) throws IOException {
        isNewSourceKeyword(source, keyWord);
        return getSentenceList();
    }



    private void analyze(InputStream inputStream, String keyWord) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                    calculate(line,keyWord);
            }
        }
    }

    private void calculate(String text, String keyWord) {
        int index = 0;
        String[] sentences = text.split("[.!?]\\S+");
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyWord.toLowerCase())) {
                sentenceList.add(sentence);
                while (index < sentence.length() &&
                        (index = sentence.toLowerCase().indexOf(keyWord.toLowerCase(), index)) >= 0) {
                    countWords++;
                    index = index + keyWord.length();
                }
            }
        }
    }

    private void isNewSourceKeyword(String source, String keyWord) throws IOException {
        if (!source.equals(this.source) || !keyWord.equals(this.keyWord)) {
            countWords = 0;
            sentenceList.clear();
            FileInputStream input = new FileInputStream(source);
            analyze(input, keyWord);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Keyword <").append(keyWord).append("> occurs in ")
                .append(source).append(" ").append(countWords).append(" times ");
        if(sentenceList.size() > 0){
            result.append(sentenceList.size()).append(" sentences: \n");
            for(String sentence : sentenceList){
                result.append(sentence).append("\n");
            }
        }
        return result.toString();
    }
}
