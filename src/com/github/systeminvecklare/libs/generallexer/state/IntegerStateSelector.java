package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;
import com.github.systeminvecklare.libs.generallexer.token.IntegerToken;

public class IntegerStateSelector implements IStateSelector {
	private final float priority;
	
	public IntegerStateSelector() {
		this(1f);
	}
	
	public IntegerStateSelector(float priority) {
		this.priority = priority;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(charStream.hasNext() && Character.isDigit(charStream.peak())) {
			return priority;
		}
		return 0;
	}

	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				StringBuilder builder = new StringBuilder();
				int lastParsedInteger = 0;
				while(charStream.hasNext()) {
					builder.append(charStream.peak());
					try {
						lastParsedInteger = Integer.parseInt(builder.toString());
						charStream.skip(1);
					} catch (NumberFormatException e) {
						tokenSink.accept(createToken(lastParsedInteger));
						return;
					}
				}
				tokenSink.accept(createToken(lastParsedInteger));
			}
		};
	}
	
	protected IToken createToken(int value) {
		return new IntegerToken(value);
	}
}
