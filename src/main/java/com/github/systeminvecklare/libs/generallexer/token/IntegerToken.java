package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class IntegerToken implements IToken {
	private final int value;
	private final Span span;

	public IntegerToken(int value, Span span) {
		this.value = value;
		this.span = span;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Integer["+value+"]";
	}

	@Override
	public Span getSpan() {
		return span;
	}
}
