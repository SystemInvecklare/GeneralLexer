package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public class SkipWhitespaceStateSelector implements IStateSelector {
	private final float priority;
	
	public SkipWhitespaceStateSelector() {
		this(0.5f);
	}
	
	public SkipWhitespaceStateSelector(float priority) {
		this.priority = priority;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(charStream.hasNext() && isWhitespace(charStream.peak())) {
			return priority;
		}
		return 0f;
	}
	
	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				while(charStream.hasNext() && isWhitespace(charStream.peak())) {
					charStream.skip(1);
				}
			}
		};
	}
	
	protected boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
