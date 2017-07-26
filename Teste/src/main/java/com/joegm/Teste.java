package com.joegm;

/**
 * Classe teste para desenvolver mas existe a clase de teste em Junit
 **/
public class Teste {

	public static void main(String[] args) {

		try {
			Character c= RetornaVogal.getInstance().firstChar(new CharStream("aAbBABacafesddeffduausodí"));
			System.out.println(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
