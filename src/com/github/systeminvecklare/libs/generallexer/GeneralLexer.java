package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.github.systeminvecklare.libs.generallexer.state.GroupStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.ILexerState;
import com.github.systeminvecklare.libs.generallexer.state.IStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.IntegerStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.LineCommentStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.MultiLineCommentStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.NameStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.SkipWhitespaceStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.StringStateSelector;
import com.github.systeminvecklare.libs.generallexer.state.SymbolStateSelector;
import com.github.systeminvecklare.libs.generallexer.token.IToken;
import com.github.systeminvecklare.libs.generallexer.token.LineBreakToken;
import com.github.systeminvecklare.libs.generallexer.token.SymbolToken;
import com.github.systeminvecklare.libs.generallexer.token.TextBlockToken;

public class GeneralLexer {
	private final List<IStateSelector> stateSelectors = new ArrayList<>();
	
	public GeneralLexer() {
	}
	
	public static GeneralLexer asmLexer() {
		return new GeneralLexer()
				.addStateSelector(new NameStateSelector())
				.addStateSelector(new StringStateSelector("\""))
				.addStateSelector(new StringStateSelector("\'"))
				.addStateSelector(new GroupStateSelector("{", "}"))
				.addStateSelector(new GroupStateSelector("(", ")"))
				.addStateSelector(new GroupStateSelector("[", "]"))
				.addStateSelector(new SkipWhitespaceStateSelector())
				.addStateSelector(new IntegerStateSelector())
				.addStateSelector(new LineCommentStateSelector(";").keepLineBreak())
				.addStateSelector(SymbolStateSelector.builder().addChars(".,+-*/:").build())
				.addStateSelector(SymbolStateSelector.builder().priority(2).addSymbol('\n').addSymbol("\r\n").makeToken(new Function<String, IToken>() {
					@Override
					public IToken apply(String t) {
						return LineBreakToken.INSTANCE;
					}
				}).build());
	}
	
	public static GeneralLexer javaLexer() {
		return new GeneralLexer()
				.addStateSelector(new NameStateSelector())
				.addStateSelector(new StringStateSelector("\""))
				.addStateSelector(new StringStateSelector("\"\"\"", 2) {
					@Override
					protected IToken createToken(String string) {
						return new TextBlockToken(string);
					}
				})
				.addStateSelector(new StringStateSelector("\'")) //TODO create special for ''
				.addStateSelector(new GroupStateSelector("{", "}"))
				.addStateSelector(new GroupStateSelector("[", "]"))
				.addStateSelector(new GroupStateSelector("(", ")"))
				.addStateSelector(new SkipWhitespaceStateSelector())
				.addStateSelector(new LineCommentStateSelector("//"))
				.addStateSelector(new MultiLineCommentStateSelector("/*", "*/"))
				.addStateSelector(SymbolStateSelector.builder().addChars(".;:=,<>+-*/@!&|").build())
				.addStateSelector(new IntegerStateSelector());
	}
	
	public static GeneralLexer genericLexer() {
		return new GeneralLexer()
			.addStateSelector(new NameStateSelector())
			.addStateSelector(new StringStateSelector("\""))
			.addStateSelector(new StringStateSelector("\'"))
			.addStateSelector(new GroupStateSelector("{", "}"))
			.addStateSelector(new GroupStateSelector("[", "]"))
			.addStateSelector(new GroupStateSelector("(", ")"))
			.addStateSelector(new SkipWhitespaceStateSelector())
			.addStateSelector(new IStateSelector() {
				@Override
				public float getMatch(ICharStream charStream) throws IOException {
					if(charStream.hasNext()) {
						return 0.001f;
					}
					return 0;
				}
				
				@Override
				public ILexerState createState() {
					return new ILexerState() {
						@Override
						public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
							tokenSink.accept(new SymbolToken(String.valueOf(charStream.next())));
						}
					};
				}
			});
	}
	
	public GeneralLexer addStateSelector(IStateSelector stateSelector) {
		stateSelectors.add(stateSelector);
		return this;
	}
	
	public GeneralLexer addStateSelectors(Iterable<IStateSelector> stateSelectors) {
		for(IStateSelector stateSelector : stateSelectors) {
			this.stateSelectors.add(stateSelector);
		}
		return this;
	}
	
	public void lex(Reader reader, Consumer<IToken> tokenSink) throws IOException {
		ICharStream charStream = new CharStream(reader);
		
		ILexerContext lexerContext = new ILexerContext() {
			@Override
			public IStateSelector findBestMatch(ICharStream charStream) throws IOException {
				float bestMatch = 0;
				IStateSelector chosenStateSelector = null;
				for(IStateSelector stateSelector : stateSelectors) {
					float match = stateSelector.getMatch(new TentativeCharStream(charStream));
					if(match > 0 && match > bestMatch) {
						bestMatch = match;
						chosenStateSelector = stateSelector;
					}
				}
				return chosenStateSelector;
			}
		};
		
		while(charStream.hasNext()) {
			IStateSelector chosenStateSelector = lexerContext.findBestMatch(charStream);
			if(chosenStateSelector != null) {
				ILexerState state = chosenStateSelector.createState();
				state.lex(charStream, tokenSink, lexerContext);
			} else {
				throw new RuntimeException("No state found! "+charStream.peak());
			}
		}
	}

	private static class TentativeCharStream implements ICharStream {
		private final ICharStream wrapped;
		private int read = 0;
		
		public TentativeCharStream(ICharStream wrapped) {
			this.wrapped = wrapped;
		}
		
		@Override
		public boolean hasNext() throws IOException {
			return wrapped.hasNext(read + 1);
		}

		@Override
		public boolean hasNext(int chars) throws IOException {
			return wrapped.hasNext(chars + read);
		}

		@Override
		public char next() throws IOException {
			return wrapped.peak(read++);
		}

		@Override
		public char peak() throws IOException {
			return wrapped.peak(read);
		}

		@Override
		public char peak(int skipped) throws IOException {
			return wrapped.peak(skipped + read);
		}

		@Override
		public void skip(int skip) throws IOException {
			read += skip;
		}
	}
}
