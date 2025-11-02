package com.github.systeminvecklare.libs.generallexer.token;

public class StringToken implements IToken {
	private final String string;

	public StringToken(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return "String["+string+"]";
	}
}
