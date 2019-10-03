package com.keelim.temp1.db;

import java.io.Serializable;

public class WordMean implements Serializable {
    private int id;
    private String word;
    private String mean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
