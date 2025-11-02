package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;
import com.github.systeminvecklare.libs.generallexer.token.StringToken;

public class StringStateSelector implements IStateSelector {
	private final String start;
	private final String end;
	private final float priority;
	
	public StringStateSelector(String symbol) {
		this(symbol, symbol);
	}
	
	public StringStateSelector(String symbol, float priority) {
		this(symbol, symbol, priority);
	}
	
	public StringStateSelector(String start, String end) {
		this(start, end, 1f);
	}

	public StringStateSelector(String start, String end, float priority) {
		this.start = start;
		this.end = end;
		this.priority = priority;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(CharStreamUtil.peakMatches(charStream, start)) {
			return priority;
		}
		return 0;
	}
	
	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				charStream.skip(start.length());
				StringBuilder stringBuilder = new StringBuilder();
				boolean escaped = false;
				while(charStream.hasNext()) {
					if(escaped) {
						stringBuilder.append(charStream.next());
						escaped = false;
					} else {
						char c = charStream.peak();
						if(c == '\\') {
							charStream.skip(1);
							escaped = true;
						} else if(CharStreamUtil.peakMatches(charStream, end)) {
							charStream.skip(end.length());
							tokenSink.accept(createToken(stringBuilder.toString()));
							return;
						} else {
							charStream.skip(1);
							stringBuilder.append(c);
						}
					}
				}
				throw new RuntimeException("Expected '"+end+"'!"); //TODO use context to throw at location
			}
		};
	}
	
	protected IToken createToken(String string) {
		return new StringToken(string);
	}
}
