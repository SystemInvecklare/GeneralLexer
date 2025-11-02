package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;
import com.github.systeminvecklare.libs.generallexer.token.SymbolToken;

public class SymbolStateSelector implements IStateSelector {
	public static class SymbolStateSelectorBuilder {
		private float priority = 1f;
		private final List<String> symbols = new ArrayList<String>();
		private Function<String, IToken> makeToken = new Function<String, IToken>() {
			@Override
			public IToken apply(String t) {
				return new SymbolToken(t);
			}
		};
		
		public SymbolStateSelectorBuilder priority(float priority) {
			this.priority = priority;
			return this;
		}
		
		public SymbolStateSelectorBuilder addChars(String chars) {
			for(char c : chars.toCharArray()) {
				addSymbol(c);
			}
			return this;
		}
		
		public SymbolStateSelectorBuilder addSymbol(char c) {
			return addSymbol(String.valueOf(c));
		}
		
		public SymbolStateSelectorBuilder addSymbol(String symbol) {
			symbols.add(symbol);
			return this;
		}
		
		public SymbolStateSelectorBuilder makeToken(Function<String, IToken> makeToken) {
			this.makeToken = makeToken;
			return this;
		}
		
		public SymbolStateSelector build() {
			return new SymbolStateSelector(symbols.toArray(new String[0]), priority, makeToken);
		}
	}
	
	public static SymbolStateSelectorBuilder builder() {
		return new SymbolStateSelectorBuilder();
	}
	
	private final String[] symbols;
	private final float priority;
	private final Function<String, IToken> makeToken;
	
	private SymbolStateSelector(String[] symbols, float priority, Function<String, IToken> makeToken) {
		this.symbols = symbols;
		this.priority = priority;
		this.makeToken = makeToken;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		for(String symbol : symbols) {
			if(CharStreamUtil.peakMatches(charStream, symbol)) {
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
				String matchingSymbol = null;
				for(String symbol : symbols) {
					if(CharStreamUtil.peakMatches(charStream, symbol)) {
						if(matchingSymbol == null || symbol.length() > matchingSymbol.length()) {
							matchingSymbol = symbol;
						}
					}
				}
				if(matchingSymbol == null) {
					throw new RuntimeException("No matching symbol");
				}
				
				charStream.skip(matchingSymbol.length());
				tokenSink.accept(makeToken.apply(matchingSymbol));
			}
		};
	}
}
