package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class TextBlockToken implements IToken {
	private final String string;
	private final Span span;

	public TextBlockToken(String string, Span span) {
		this.string = string;
		this.span = span;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return "TextBlock["+string+"]";
	}
	
	@Override
	public Span getSpan() {
		return span;
	}
}
