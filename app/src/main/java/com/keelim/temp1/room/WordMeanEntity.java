package com.keelim.temp1.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class WordMeanEntity {
    @PrimaryKey
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
