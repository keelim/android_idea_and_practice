package com.keelim.temp1.db;

import android.util.Log;

import com.keelim.temp1.utils.WordDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryLoader {

    public static void loadData(BufferedReader bufferedReader, DictionaryDatabaseHelper dictionaryDatabaseHelper) {
        String logTagString = "DICTIONARY";
        ArrayList<WordDefinition> allWords = new ArrayList<WordDefinition>();

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
                            // TODO Auto-generated catch block
                            System.out.println(stringBuilder.length());
                            //e.printStackTrace();
                        }

                        c = fileReader.read();
                    }
                    String wordString = stringBuilder.toString();
                    ArrayList<String> definition = new ArrayList<String>();

                    while (c == 10 ) {
                        c = fileReader.read();
                        //Log.d(logTagString, "Definition first: "+String.valueOf(c));
                        if ((c != 10 && c != 13)&& c!=-1) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            while (c != 10 && c!=-1) {
                                stringBuilder2.append((char) c);
                                c = fileReader.read();
                            }
//                            Log.d(logTagString, "inside if: "+String.valueOf(c));
                            String definitionString = stringBuilder2.toString();
//                            Log.d(logTagString,"Add line");
                            definition.add(definitionString);
                        } else {
                            c = fileReader.read();
                            c = fileReader.read();
//                            Log.d(logTagString,"else: " + String.valueOf(c));
                            break;
                        }
                    }
//                    c = fileReader.read();
                    wordString = wordString.trim();
                    Log.d(logTagString,"Setting definition");
                    allWords.add(new WordDefinition(wordString, definition));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {

                dictionaryDatabaseHelper.initializeDatabaseFortheFirstTime(allWords);
                fileReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
