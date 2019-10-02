package com.keelim.temp1.db;

import android.util.Log;

import com.keelim.temp1.utils.WordDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public final class DictionaryLoader { //파일을 로더 하는 것 데이터 베이스로 바꿀 필요가 있다.

    public static void loadData(BufferedReader bufferedReader, DictionaryDatabaseHelper dictionaryDatabaseHelper) {
        ArrayList<WordDefinition> allWords = new ArrayList<>();

        try {
            int c = bufferedReader.read();
            while (c != (-1)) {
                StringBuilder stringBuilder = new StringBuilder();

                while (c != 10 && c != -1) {
                    stringBuilder.append((char) c);
                    c = bufferedReader.read();
                }

                ArrayList<String> definition = new ArrayList<>();

                while (c == 10) {
                    c = bufferedReader.read();
                    if ((c != 10 && c != 13) && c != -1) {
                        StringBuilder temp = new StringBuilder();

                        while (c != 10 && c != -1) {
                            temp.append((char) c);
                            c = bufferedReader.read();
                        }

                        String definitionString = temp.toString();
                        definition.add(definitionString);
                    } else {
                        c = bufferedReader.read();
                        break;
                    }
                }

                String wordString = stringBuilder.toString().trim();
                allWords.add(new WordDefinition(wordString, definition));

                dictionaryDatabaseHelper.initializeDatabaseFortheFirstTime(allWords);
                bufferedReader.close();
            }

        } catch (IOException e) {
            Log.e("error", e.getMessage());
        }
    }
}
