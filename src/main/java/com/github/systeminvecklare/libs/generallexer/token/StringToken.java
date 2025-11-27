package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class StringToken implements IToken {
	private final String string;
	private final Span span;

	public StringToken(String string, Span span) {
		this.string = string;
		this.span = span;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return "String["+string+"]";
	}

	@Override
	public Span getSpan() {
		return span;
	}
}
