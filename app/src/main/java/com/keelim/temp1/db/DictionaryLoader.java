package com.keelim.temp1.db;

import android.util.Log;

import com.keelim.temp1.utils.WordDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryLoader { //파일을 로더 하는 것 데이터 베이스로 바꿀 필요가 있다.

    public static void loadData(BufferedReader bufferedReader, DictionaryDatabaseHelper dictionaryDatabaseHelper) {
        String logTagString = "DICTIONARY";
        ArrayList<WordDefinition> allWords = new ArrayList<>();

        try {
            BufferedReader fileReader = bufferedReader;
            try {
                Log.d(logTagString, "Inside loader");
                int c;
                c = fileReader.read();
                while (c != (-1)) {
                    StringBuilder stringBuilder = new StringBuilder();

                    while ( c != 10 && c != -1) {
                        try {
                            stringBuilder.append((char) c);
                        } catch (Exception e) {
                            System.out.println(stringBuilder.length());
                            Log.e("error", e.getMessage());
                        }
                        c = fileReader.read();
                    }
                    String wordString = stringBuilder.toString();
                    ArrayList<String> definition = new ArrayList<>();

                    while (c == 10 ) {
                        c = fileReader.read();
                        if ((c != 10 && c != 13)&& c!=-1) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            while (c != 10 && c!=-1) {
                                stringBuilder2.append((char) c);
                                c = fileReader.read();
                            }
                            String definitionString = stringBuilder2.toString();
                            definition.add(definitionString);
                        } else {
                            c = fileReader.read();
                            c = fileReader.read();
                            break;
                        }
                    }
                    wordString = wordString.trim();
                    Log.d(logTagString,"Setting definition");
                    allWords.add(new WordDefinition(wordString, definition));
                }
            } catch (IOException e) {
                Log.e("error", e.getMessage());
            }
            try {
                dictionaryDatabaseHelper.initializeDatabaseFortheFirstTime(allWords);
                fileReader.close();

            } catch (IOException e) {
                Log.e("error", e.getMessage());
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

    }
}
