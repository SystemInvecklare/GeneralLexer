package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
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
				charStream.skip(start.length());
				List<IToken> groupTokens = new ArrayList<IToken>();
				while(charStream.hasNext()) {
					if(CharStreamUtil.peakMatches(charStream, end)) {
						charStream.skip(end.length());
						tokenSink.accept(createToken(start, end, groupTokens));
						return;
					} else {
						IStateSelector bestMatch = lexerContext.findBestMatch(charStream);
						if(bestMatch == null) {
							throw new RuntimeException("Failed at '"+charStream.next()+"'");
							//TODO throw because we failed to parse inside
						}
						bestMatch.createState().lex(charStream, new Consumer<IToken>() {
							@Override
							public void accept(IToken token) {
								groupTokens.add(token);
							}
						}, lexerContext);
					}
				}
				throw new RuntimeException("Expected '"+end+"'!"); //TODO use context to throw at location
			}
		};
	}

	protected IToken createToken(String start, String end, List<IToken> groupTokens) {
		return new GroupToken(start, end, groupTokens);
	}
}
