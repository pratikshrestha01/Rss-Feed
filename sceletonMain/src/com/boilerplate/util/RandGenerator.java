package com.boilerplate.util;

public class RandGenerator {
	
	public static String getRanCode(int length) {

		String mystr = "abcdefghijklmnopqrstuvwxyz";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}
		
		return restr;

	}
	
	public static String getRanAlphaNum(int length) {

		String mystr = "abcdefghijklmnopqrstuvwxyz1234567890";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}
		
		return restr;

	}
	
	
	public static String getPassGenerator(int length) {

		String mystr = "abcdefghijklmnopqrstuvwxyz1234567890";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}
		
		return restr;

	}
	
	public static String getRanGeneratorNum(int length) {

		String mystr = "1234567890";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}
		
		return restr;

	}

}
