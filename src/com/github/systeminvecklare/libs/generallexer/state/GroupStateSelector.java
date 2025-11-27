package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.span.GeneralLexerRuntimeException;
import com.github.systeminvecklare.libs.generallexer.span.Offset;
import com.github.systeminvecklare.libs.generallexer.span.Span;
import com.github.systeminvecklare.libs.generallexer.token.GroupToken;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public class GroupStateSelector implements IStateSelector {
	private final String start;
	private final String end;
	private final float priority;
	
	public GroupStateSelector(String start, String end) {
		this(start, end, 1f);
	}

	public GroupStateSelector(String start, String end, float priority) {
		this.start = start;
		this.end = end;
		this.priority = priority;
	}
	
	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(CharStreamUtil.peakMatches(charStream, start)) {
			return priority;
		}
		return 0f;
	}
	
	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				Offset spanStart = charStream.getOffset();
				charStream.skip(start.length());
				Offset tokensSpanStart = charStream.getOffset();
				List<IToken> groupTokens = new ArrayList<IToken>();
				while(charStream.hasNext()) {
					if(CharStreamUtil.peakMatches(charStream, end)) {
						Span tokensSpan = new Span(tokensSpanStart, charStream.getOffset());
						charStream.skip(end.length());
						tokenSink.accept(createToken(start, end, groupTokens, new Span(spanStart, charStream.getOffset()), tokensSpan));
						return;
					} else {
						IStateSelector bestMatch = lexerContext.findBestMatch(charStream);
						if(bestMatch == null) {
							throw new GeneralLexerRuntimeException("Failed at '"+charStream.next()+"'", Span.singleCharacter(charStream.getOffset()));
						}
						bestMatch.createState().lex(charStream, new Consumer<IToken>() {
							@Override
							public void accept(IToken token) {
								groupTokens.add(token);
							}
						}, lexerContext);
					}
				}
				throw new GeneralLexerRuntimeException("Expected '"+end+"'!", Span.singleCharacter(charStream.getOffset()));
			}
		};
	}

	protected IToken createToken(String start, String end, List<IToken> groupTokens, Span span, Span tokensSpan) {
		return new GroupToken(start, end, groupTokens, span, tokensSpan);
	}
}
