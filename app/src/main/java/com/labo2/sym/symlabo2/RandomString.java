package com.labo2.sym.symlabo2;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Classe utilisée pour générer des string de longueur aléatoire pour étudier le gain obtenu par la compression
 */
public class RandomString {
	
	private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lower = "abcdefghijklmnopqrstuvwxyz";
	private static final String digits = "0123456789";
	private static final String alphanum = upper + lower + digits;
	private final Random random;
	private final char[] symbols;
	private final char[] buf;
	
	/**
	 * Constructeur
	 */
	public RandomString() {
		this.random = new SecureRandom();
		this.symbols = alphanum.toCharArray();
		int length = random.nextInt(5000) + 1;
		this.buf = new char[length];
	}
	
	/**
	 * Génère une String de longueur aléatoire avec des caractères alphanumériques aléatoire.
	 */
	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}
}
