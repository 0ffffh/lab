package com.k0s.io.fileanalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer extends TextAnalyzer{
    private String keyWord;
    private String source;
    private int countWords;
    private int countSentences;
    private List<String> sentenceList = new ArrayList<>();

    public FileAnalyzer(){}


    public FileAnalyzer(String source, String keyWord) throws IOException {
        this.keyWord = keyWord;
        this.source = source;
        init(source, keyWord);
    }

    @Override
    public int getCountWords(String source, String keyWord) throws IOException {
        init(source, keyWord);
        return getCountWords();
    }
    public int getCountWords() {
        return countWords;
    }


    @Override
    public int getCountSentences(String source, String keyWord) throws IOException {
        init(source, keyWord);
        return getCountSentences();
    }

    public int getCountSentences() {
        return countSentences;
    }

    @Override
    public List<String> getSentenceList(String source, String keyWord) throws IOException {
        init(source, keyWord);
        return getSentenceList();
    }


    public List<String> getSentenceList() {
        return sentenceList;
    }


    private void init(String source, String keyWord) throws IOException {
        countWords = 0;
        countSentences = 0;
        sentenceList.clear();
        FileInputStream input = new FileInputStream(source);
        analyze(input, keyWord);
    }

    private void analyze(InputStream inputStream, String keyWord) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                countWords += super.getCountWords(line, keyWord);
                sentenceList.addAll(super.getSentenceList(line, keyWord));
                countSentences += super.getCountSentences(line, keyWord);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Keyword <").append(keyWord).append("> occurs in ")
                .append(source).append(" ").append(countWords).append(" times ");
        if(countSentences > 0){
            result.append(countSentences).append(" sentences:").append("\n");
            for(String sentence : sentenceList){
                result.append(sentence).append("\n");
            }
        }
        return result.toString();
    }
}
