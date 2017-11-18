package com.test.signature;

public class Token {
	private String _chars;

	public Token(String chars) {
		_chars = chars;
	}

	public String getChars() {
		return _chars;
	}

	public boolean isArray() {
		return _chars.equals("[");
	}

	public boolean isGenericSeparator() {
		return _chars.equals(";");
	}

	public boolean isGenericStart() {
		return _chars.equals("<");
	}

	public boolean isGenericStop() {
		return _chars.equals(">");
	}

	public boolean isTypeName() {
		return _chars.matches("[a-zA-Z0-9.]+");
	}
}
