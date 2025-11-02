package com.github.systeminvecklare.libs.generallexer.token;

import java.util.List;

public class GroupToken implements IToken {
	private final String start;
	private final String end;
	private final List<IToken> tokens;

	public GroupToken(String start, String end, List<IToken> tokens) {
		this.start = start;
		this.end = end;
		this.tokens = tokens;
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
}
