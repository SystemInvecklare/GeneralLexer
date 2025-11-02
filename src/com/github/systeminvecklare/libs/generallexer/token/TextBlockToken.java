package com.github.systeminvecklare.libs.generallexer.token;

public class TextBlockToken implements IToken {
	private final String string;

	public TextBlockToken(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return "TextBlock["+string+"]";
	}
}
