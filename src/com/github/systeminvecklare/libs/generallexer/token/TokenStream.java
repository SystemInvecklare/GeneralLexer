package com.github.systeminvecklare.libs.generallexer.token;

import java.util.Collections;
import java.util.List;

public class TokenStream implements ITokenStream {
	private final List<IToken> tokens;
	private int next = 0;
	
	public TokenStream(List<IToken> tokens) {
		this.tokens = tokens;
	}

	@Override
	public boolean hasNext() {
		return hasNext(1);
	}

	@Override
	public boolean hasNext(int chars) {
		return next + chars - 1 < tokens.size();
	}

	@Override
	public IToken peak() {
		return tokens.get(next);
	}

	@Override
	public IToken peak(int skipped) {
		return tokens.get(next + skipped);
	}

	@Override
	public IToken next() {
		return tokens.get(next++);
	}

	@Override
	public void skip(int skip) {
		next += skip;
	}

	public static TokenStream empty() {
		return new TokenStream(Collections.emptyList());
	}
}
