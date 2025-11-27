package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;

import com.github.systeminvecklare.libs.generallexer.state.IStateSelector;

public interface ILexerContext {
	IStateSelector findBestMatch(ICharStream charStream) throws IOException;
}
