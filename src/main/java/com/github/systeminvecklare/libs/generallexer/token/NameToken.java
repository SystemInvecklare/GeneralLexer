package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class NameToken implements IToken {
	private final String name;
	private final Span span;

	public NameToken(String name, Span span) {
		this.name = name;
		this.span = span;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Name["+name+"]";
	}
	
	@Override
	public Span getSpan() {
		return span;
	}
}
