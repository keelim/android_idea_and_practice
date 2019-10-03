package com.keelim.temp1.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM wordmeanentity")
    List<WordMeanEntity> getAll();

    @Query("SELECT * FROM wordmeanentity WHERE word")
    List<WordMeanEntity> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM wordmeanentity WHERE word LIKE :first")
    WordMeanEntity findByName(String first, String last);

    @Insert
    void insertAll(WordMeanEntity... wordMeanEntities);

    @Delete
    void delete(WordMeanEntity wordMeanEntity);
}
