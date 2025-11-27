package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.ICharStream;
import com.github.systeminvecklare.libs.generallexer.ILexerContext;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public interface ILexerState {
	void lex(ICharStream charStream, Consumer<IToken> tokenSink, ILexerContext lexerContext) throws IOException;
}
