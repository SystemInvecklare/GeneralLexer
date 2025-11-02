package com.github.systeminvecklare.libs.generallexer.token;

public class LineBreakToken implements IToken {
	public static final IToken INSTANCE = new LineBreakToken();

	@Override
	public String toString() {
		return "LineBreak[]";
	}
}
