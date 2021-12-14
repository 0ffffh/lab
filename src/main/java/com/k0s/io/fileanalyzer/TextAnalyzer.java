package com.k0s.io.fileanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class TextAnalyzer {


    public int getCountWords(String text, String keyWord) throws IOException {

        return countWord(text, keyWord);
    }

    public int getCountSentences(String text, String keyWord) throws IOException {
        return countSentences(text,  keyWord);
    }

    public List<String> getSentenceList(String text, String keyWord) throws IOException {
        return sentencesToList(text, keyWord);
    }




    private int countSentences(String text, String keyWord) {
        int count = 0;
        String[] sentences = text.split("[.!?]\\S+");
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyWord.toLowerCase())) {
                count++;
            }
        }
        return count;
    }


    private List<String> sentencesToList(String text, String keyWord) {
        List<String> sentenceList = new ArrayList<>();
        String[] sentences = text.split("[.!?]\\S+");
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyWord.toLowerCase())) {
                sentenceList.add(sentence);
            }
        }
        return sentenceList;
    }


    private int countWord(String text, String keyWord) {
        int index = 0;
        int count = 0;
        String[] sentences = text.split("[.!?]\\S+");
        for (String sentence : sentences) {
            while (index < sentence.length() &&
                    (index = sentence.toLowerCase().indexOf(keyWord.toLowerCase(), index)) >= 0) {
                count++;
                index = index + keyWord.length();
            }
        }
        return count;
    }
}

