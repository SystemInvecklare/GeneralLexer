package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class SymbolToken implements IToken {
	private final String symbol;
	private final Span span;

	public SymbolToken(String symbol, Span span) {
		this.symbol = symbol;
		this.span = span;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return "Symbol["+symbol+"]";
	}

	@Override
	public Span getSpan() {
		return span;
	}
}
