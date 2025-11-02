package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;
import com.github.systeminvecklare.libs.generallexer.token.NameToken;

public class NameStateSelector implements IStateSelector {
	private final float priority;
	
	public NameStateSelector() {
		this(1f);
	}
	
	public NameStateSelector(float priority) {
		this.priority = priority;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(charStream.hasNext()) {
			if(isValidStart(charStream.next())) {
				return priority;
			}
		}
		return 0;
	}

	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(charStream.next());
				while(charStream.hasNext()) {
					if(!isValidPart(charStream.peak())) {
						break;
					}
					nameBuilder.append(charStream.next());
				}
				tokenSink.accept(createToken(nameBuilder.toString()));
			}
		};
	}
	
	protected boolean isValidStart(char c) {
		return Character.isJavaIdentifierStart(c);
	}
	
	protected boolean isValidPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}
	
	protected IToken createToken(String name) {
		return new NameToken(name);
	}
}
