package com.github.systeminvecklare.libs.generallexer.token;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class LineBreakToken implements IToken {
	private final Span span;
	
	public LineBreakToken(Span span) {
		this.span = span;
	}

	@Override
	public String toString() {
		return "LineBreak[]";
	}
	
	@Override
	public Span getSpan() {
		return span;
	}
}
