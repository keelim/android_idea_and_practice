package com.keelim.temp1.utils;

import java.util.ArrayList;

public class WordDefinition {
	public String word,definition;
	
	public WordDefinition(String word,ArrayList<String> alldefinition) {
		this.word=word;
		
		StringBuilder stringBuilder=new StringBuilder();
		for (String string : alldefinition) {
			stringBuilder.append(string);
		}		
		this.definition=stringBuilder.toString();
		
	}
	
	public WordDefinition(String word,String alldefinition) {
		this.word=word;		
		this.definition=alldefinition;
		
	}
	
	
}
