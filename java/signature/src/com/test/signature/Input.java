package com.test.signature;

import java.util.ArrayList;
import java.util.List;

public class Input {
	private List<Token> _tokens;
	private int _index;

	public Input(String input) {
		this._tokens = tokenize(input);
		this._index = 0;
	}

	private static List<Token> tokenize(String input) {
		List<Token> result = new ArrayList<Token>();

		String token = "";
		for (int index = 0; index < input.length(); index++) {
			String current = "" + input.charAt(index);

			// If we find a character that is not a valid continuation of the last one
			// let's push the tokens
			if (current.matches("[a-zA-Z0-9.]+")) {
				// Still a valid character, remember it
				token = token + current;
			} else {
				// If we already remembered something from previous iterations, push
				if (!token.equals("")) {
					result.add(new Token(token));
					token = "";
				}

				// Push the stop character
				result.add(new Token(current));
			}
		}

		return result;
	}

	public boolean hasData() {
		return this._index < this._tokens.size();
	}

	public Token peek() {
		return this._tokens.get(this._index);
	}

	public Token consume() {
		Token next = peek();
		this._index = this._index + 1;
		return next;
	}
}
