package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.span.GeneralLexerRuntimeException;
import com.github.systeminvecklare.libs.generallexer.span.Span;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public class MultiLineCommentStateSelector implements IStateSelector {
	private final String start;
	private final String end;
	private final float priority;
	
	public MultiLineCommentStateSelector(String symbol) {
		this(symbol, symbol);
	}
	
	public MultiLineCommentStateSelector(String symbol, float priority) {
		this(symbol, symbol, priority);
	}
	
	public MultiLineCommentStateSelector(String start, String end) {
		this(start, end, 1f);
	}

	public MultiLineCommentStateSelector(String start, String end, float priority) {
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
				while(charStream.hasNext()) {
					if(CharStreamUtil.peakMatches(charStream, end)) {
						charStream.skip(end.length());
						onComment(stringBuilder.toString(), tokenSink);
						return;
					} else {
						stringBuilder.append(charStream.next());
					}
				}
				throw new GeneralLexerRuntimeException("Expected '"+end+"'!", Span.singleCharacter(charStream.getOffset()));
			}
		};
	}
	
	protected void onComment(String comment, Consumer<IToken> tokenSink) {
	}
}
