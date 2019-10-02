package com.keelim.temp1.utils;

import java.util.ArrayList;

public class WordDefinition {
    public String word;
    public String definition;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public WordDefinition(String word, String allDefinition) {
        this.word = word;
        this.definition = allDefinition;
    }

    public WordDefinition(String word, ArrayList<String> allDefinition) {
        setWord(word);
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : allDefinition) {
            stringBuilder.append(string);
        }

		setDefinition(stringBuilder.toString());
    }
}
