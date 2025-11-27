package com.github.systeminvecklare.libs.generallexer.token;

import java.util.List;

import com.github.systeminvecklare.libs.generallexer.span.Span;

public class GroupToken implements IToken {
	private final String start;
	private final String end;
	private final List<IToken> tokens;
	private final Span span;
	private final Span tokensSpan;

	public GroupToken(String start, String end, List<IToken> tokens, Span span, Span tokensSpan) {
		this.start = start;
		this.end = end;
		this.tokens = tokens;
		this.span = span;
		this.tokensSpan = tokensSpan;
	}
	
	public String getStart() {
		return start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public List<IToken> getTokens() {
		return tokens;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group START "+start+"\n");
		for(IToken token : tokens) {
			builder.append(token.toString()).append("\n");
		}
		builder.append("Group END "+end);
		return builder.toString();
	}
	
	@Override
	public Span getSpan() {
		return span;
	}
	
	public Span getTokensSpan() {
		return tokensSpan;
	}
}
