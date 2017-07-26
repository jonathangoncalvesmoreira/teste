package com.joegm;

import java.util.NoSuchElementException;

public class CharStream implements Stream {
	private char[] stream;
	private int count = 0;

	public CharStream(String text) throws IllegalArgumentException {

		if (text == null) {
			throw new IllegalArgumentException();
		}
		stream = text.toCharArray();

	}

	@Override
	public char getNext() {
		try {
			return stream[count++];
		} catch (Throwable e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public boolean hasNext() {
		return count < stream.length;
	}

}
