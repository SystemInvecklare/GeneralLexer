package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.CharStreamUtil;
import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public class LineCommentStateSelector implements IStateSelector {
	private final String lineCommentStart;
	private final float priority;
	private boolean keepLineBreak = false;
	
	public LineCommentStateSelector(String lineCommentStart) {
		this(lineCommentStart, 1f);
	}
	
	
	public LineCommentStateSelector(String lineCommentStart, float priority) {
		this.lineCommentStart = lineCommentStart;
		this.priority = priority;
	}

	@Override
	public float getMatch(ICharStream charStream) throws IOException {
		if(CharStreamUtil.peakMatches(charStream, lineCommentStart)) {
			return priority;
		}
		return 0f;
	}

	@Override
	public ILexerState createState() {
		return new ILexerState() {
			@Override
			public void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException {
				StringBuilder builder = new StringBuilder();
				charStream.skip(lineCommentStart.length());
				while(charStream.hasNext()) {
					char c = charStream.peak();
					if(c == '\n') {
						if(!keepLineBreak) {
							charStream.skip(1);
						}
						onComment(builder.toString(), tokenSink);
						return;
					} else if(c == '\r' && charStream.hasNext(2) && charStream.peak(1) == '\n') {
						if(!keepLineBreak) {
							charStream.skip(2);
						}
						onComment(builder.toString(), tokenSink);
						return;
					} else {
						charStream.skip(1);
						builder.append(c);
					}
				}
				onComment(builder.toString(), tokenSink);
			}
		};
	}
	
	public LineCommentStateSelector keepLineBreak() {
		keepLineBreak = true;
		return this;
	}
	
	protected void onComment(String comment, Consumer<IToken> tokenSink) {
	}
}
