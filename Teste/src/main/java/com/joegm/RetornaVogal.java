package com.joegm;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.joegm.exception.CharNotFoundException;

/**
 * Clase que segue o Pattern Singleton para ter uma unica instancia e ter melhor
 * performance.
 * 
 * @author jmoreira
 *
 */
public class RetornaVogal {
	private static RetornaVogal instance = load();

	private static final String vogais = "aeiouáéíóúàèìòùâêîôûãõAEIOUÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕ";

	private static final String[] vogalArray = new String[] { "AÁÀÂÃaáàâã", "eéèêEÉÈÊ", "iíìîIÍÌÎ", "oóòôõOÓÒÔÕ",
			"uúùûUÚÙÛ" };

	/*
	 * Criei esse map para utilizar como cache para evitar de ficar varendo o array
	 * todas as veses fiz ele syncronizado pois como a classe é siongleton pdemos
	 * ter muitas threads rodando simultaneamente e não podemos da o erro de
	 * concorrencia
	 */
	private Map<Character, String> cache = Collections.synchronizedMap(new HashMap<>());

	private static RetornaVogal load() {
		return new RetornaVogal();
	}

	public static RetornaVogal getInstance() {
		return instance;
	}

	private RetornaVogal() {

	}

	/**
	 * 
	 * @param char
	 * @return String que representa a vogal com todas suas posibilidades
	 */
	private String getEquivalente(char c) {
		String aux = cache.get(c);
		if (aux != null) {
			return aux;
		}
		for (String value : vogalArray) {
			if (value.indexOf(c) > -1) {
				cache.put(c, value);
				return value;
			}
		}
		return "";
	}

	private boolean isVogal(char c) {
		return vogais.indexOf(c) > -1;
	}

	public char firstChar(Stream stream) throws CharNotFoundException {

		boolean penultimo = false;
		boolean ultimo = false;
		boolean atual = false;

		LinkedHashMap<String, Character> mapPossiveis = new LinkedHashMap<>();

		Set<String> controle = new HashSet<>();

		while (stream.hasNext()) {
			char c = stream.getNext();
			// Verifica se o char é vogal
			atual = isVogal(c);
			if (atual) {

				// pega a String com todas as possiveis formas da mesma vogal
				String equivalente = getEquivalente(c);

				// remove do mapPossiveis a vogal caso exista, poi a vogal tem que ser única no
				// stream.
				mapPossiveis.remove(equivalente);

				// verifica se a vogal já passou pelo stream
				boolean contem = controle.contains(equivalente);

				// verifica se o penultimo é vogal, se o uiltimo é consoante, se o atual é vogal
				// e se ele ainda não se repetiu
				if (penultimo && !ultimo && atual && !contem) {

					// adiciona no mapPossiveis o char atual, tendo como a chave do map o sua String
					// equivalente.
					mapPossiveis.put(equivalente, c);
				}
				// adiciona em controle para calcular se a vogal será repetida
				controle.add(equivalente);
			}

			// pega o estado do ultimo e pasa para o penultimo para ser utilizado
			// posteriormente na regra de negocio
			penultimo = ultimo;
			// pega o estado do atual e pasa para o ultimo para ser utilizado posteriormente
			// na regra de negocio
			ultimo = atual;
		}
		// como utilizamos LinkedHashMap o keySet é na ordem de insert, então caso tenha
		// um item ele vai pegar o primeiro e retornar
		for (String key : mapPossiveis.keySet()) {
			return mapPossiveis.get(key);
		}
		// caso não tenha uma vogal que entre na regra retorna uma exception Tipada.
		throw new CharNotFoundException("Vogal não encontrada");

	}
}
