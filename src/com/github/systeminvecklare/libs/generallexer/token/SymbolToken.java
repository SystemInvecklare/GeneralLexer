package com.github.systeminvecklare.libs.generallexer.token;

public class SymbolToken implements IToken {
	private final String symbol;

	public SymbolToken(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return "Symbol["+symbol+"]";
	}
}
